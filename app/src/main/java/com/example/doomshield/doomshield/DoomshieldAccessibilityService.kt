package com.example.doomshield.doomshield

import android.accessibilityservice.AccessibilityService
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DoomshieldAccessibilityService : AccessibilityService() {

    @Inject
    lateinit var doomshieldMonitor: DoomshieldMonitor

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        event?.let {
            val packageName = it.packageName?.toString() ?: return
            
            when (it.eventType) {
                AccessibilityEvent.TYPE_VIEW_SCROLLED -> {
                    doomshieldMonitor.onScrollEvent(packageName)
                }
                AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED -> {
                    doomshieldMonitor.onAppChange(packageName)
                }
                else -> {
                    // Ignore other events for now
                }
            }
        }
    }

    override fun onInterrupt() {
        Log.d("DoomshieldService", "onInterrupt")
    }

    override fun onServiceConnected() {
        super.onServiceConnected()
        Log.d("DoomshieldService", "onServiceConnected")
        doomshieldMonitor.onDoomscrollDetected = {
            performGlobalAction(GLOBAL_ACTION_HOME)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("DoomshieldService", "onDestroy")
    }
}