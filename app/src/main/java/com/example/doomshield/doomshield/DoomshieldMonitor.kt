package com.example.doomshield.doomshield

import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.doomshield.overlay.DoomShieldOverlayService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject
import javax.inject.Singleton

import dagger.hilt.android.qualifiers.ApplicationContext

import com.example.doomshield.data.local.datastore.UserPreferencesRepository
import com.example.doomshield.domain.repository.StatsRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

@Singleton
class DoomshieldMonitor @Inject constructor(
    @ApplicationContext private val context: Context,
    private val statsRepository: StatsRepository,
    private val userPreferencesRepository: UserPreferencesRepository,
    private val checkAppBlockedUseCase: com.example.doomshield.domain.usecase.CheckAppBlockedUseCase,
    private val logAppUsageUseCase: com.example.doomshield.domain.usecase.LogAppUsageUseCase
) {

    private val scrollEvents = ConcurrentHashMap<String, MutableList<Long>>()
    private val sessionStartTimes = ConcurrentHashMap<String, Long>()
    private val appSessionStartTimes = ConcurrentHashMap<String, Long>() // For all apps, not just social media
    private var currentPackage: String? = null
    private val monitorScope = CoroutineScope(Dispatchers.Default + Job())

    companion object {
        private const val SCROLL_THRESHOLD_COUNT = 20
        private const val SCROLL_WINDOW_MS = 60_000L // 1 minute
        private const val SESSION_THRESHOLD_MS = 30 * 60_000L // 30 minutes
        private const val TAG = "DoomshieldMonitor"
        private const val TIME_SAVED_PER_INTERVENTION_MIN = 5L

        private val SOCIAL_MEDIA_PACKAGES = setOf(
            "com.instagram.android",
            "com.zhiliaoapp.musically",
            "com.ss.android.ugc.trill", // TikTok (Global)
            "com.google.android.youtube",
            "com.facebook.katana",
            "com.twitter.android",
            "com.reddit.frontpage",
            "com.snapchat.android"
        )
    }

    var onDoomscrollDetected: (() -> Unit)? = null

    fun onAppChange(packageName: String) {
        if (packageName == currentPackage) return

        // Log the previous app session (for all apps)
        currentPackage?.let { pkg ->
            val startTime = appSessionStartTimes[pkg]
            if (startTime != null && startTime > 0) {
                val endTime = System.currentTimeMillis()
                monitorScope.launch {
                    try {
                        val appName = getAppName(pkg)
                        logAppUsageUseCase(pkg, appName, startTime, endTime)
                    } catch (e: Exception) {
                        Log.e(TAG, "Error logging app usage", e)
                    }
                }
            }
            
            // Log the previous session if it was a social media app
            if (pkg in SOCIAL_MEDIA_PACKAGES) {
                val socialStartTime = sessionStartTimes[pkg] ?: 0L
                val endTime = System.currentTimeMillis()
                val scrollCount = scrollEvents[pkg]?.size ?: 0
                
                if (socialStartTime > 0) {
                    monitorScope.launch {
                        statsRepository.logSession(pkg, socialStartTime, endTime, scrollCount)
                    }
                }
            }
        }

        Log.d(TAG, "App changed to: $packageName")
        currentPackage = packageName
        
        // Start tracking session for new app
        appSessionStartTimes[packageName] = System.currentTimeMillis()
        sessionStartTimes[packageName] = System.currentTimeMillis()
        
        // Check if the new app has time limits and should be blocked
        monitorScope.launch {
            checkAndBlockIfNeeded(packageName)
        }
    }
    
    private suspend fun checkAndBlockIfNeeded(packageName: String) {
        try {
            val currentDate = com.example.doomshield.data.local.room.AppUsageSession.getDateString(System.currentTimeMillis())
            val blockStatus = checkAppBlockedUseCase(packageName, currentDate)
            
            if (blockStatus is com.example.doomshield.domain.usecase.BlockStatus.Blocked) {
                Log.w(TAG, "App $packageName is blocked due to time limit")
                triggerBlockOverlay(packageName, blockStatus)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error checking app block status", e)
        }
    }
    
    private fun triggerBlockOverlay(packageName: String, blockStatus: com.example.doomshield.domain.usecase.BlockStatus.Blocked) {
        Log.d(TAG, "Triggering Block Overlay for $packageName")
        
        // For now, use the existing overlay service
        // TODO: Create a dedicated AppBlockedOverlayService with time limit info
        val intent = Intent(context, DoomShieldOverlayService::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            context.startForegroundService(intent)
        } else {
            context.startService(intent)
        }
    }
    
    private fun getAppName(packageName: String): String {
        return try {
            val pm = context.packageManager
            val appInfo = pm.getApplicationInfo(packageName, 0)
            pm.getApplicationLabel(appInfo).toString()
        } catch (e: Exception) {
            packageName
        }
        
        // Do NOT stop the overlay automatically. It should persist until dismissed by user.
        // stopOverlayService()
    }

    fun onScrollEvent(packageName: String) {
        // Check if shield is enabled
        val isShieldEnabled = runBlocking { userPreferencesRepository.isShieldEnabled.first() }
        if (!isShieldEnabled) return

        if (packageName !in SOCIAL_MEDIA_PACKAGES) {
            return
        }

        if (packageName != currentPackage) {
            onAppChange(packageName)
        }

        val currentTime = System.currentTimeMillis()
        val events = scrollEvents.getOrPut(packageName) { mutableListOf() }
        events.add(currentTime)

        // Remove old events outside the window
        events.removeAll { it < currentTime - SCROLL_WINDOW_MS }

        Log.d(TAG, "Scroll count for $packageName: ${events.size}")

        checkDoomscrolling(packageName)
    }

    private fun checkDoomscrolling(packageName: String) {
        val events = scrollEvents[packageName] ?: return
        val sessionStart = sessionStartTimes[packageName] ?: return
        val currentTime = System.currentTimeMillis()

        // Heuristic 1: Rapid Scrolling
        if (events.size > SCROLL_THRESHOLD_COUNT) {
            Log.w(TAG, "Doomscrolling detected! Rapid scrolling in $packageName")
            triggerOverlay()
            // Clear events to prevent spamming the overlay
            events.clear()
            return
        }

        // Heuristic 2: Long Session
        if (currentTime - sessionStart > SESSION_THRESHOLD_MS) {
             Log.w(TAG, "Doomscrolling detected! Long session in $packageName")
             triggerOverlay()
             // Reset session start to prevent immediate re-trigger (or handle differently)
             sessionStartTimes[packageName] = currentTime 
        }
    }

    private fun triggerOverlay() {
        Log.d(TAG, "Triggering Overlay Service")
        
        // Update stats
        monitorScope.launch {
            userPreferencesRepository.incrementInterventions()
            userPreferencesRepository.addTimeSaved(TIME_SAVED_PER_INTERVENTION_MIN)
        }

        onDoomscrollDetected?.invoke()
        val intent = Intent(context, DoomShieldOverlayService::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            context.startForegroundService(intent)
        } else {
            context.startService(intent)
        }
    }

    private fun stopOverlayService() {
        val intent = Intent(context, DoomShieldOverlayService::class.java)
        context.stopService(intent)
    }
}
