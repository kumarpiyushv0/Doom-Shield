package com.example.doomshield.domain.usecase

import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import com.example.doomshield.domain.model.InstalledApp
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class GetInstalledAppsUseCase @Inject constructor(
    @ApplicationContext private val context: Context
) {
    operator fun invoke(): List<InstalledApp> {
        val packageManager = context.packageManager
        
        // Get all apps that have a launcher intent (user-facing apps)
        val mainIntent = Intent(Intent.ACTION_MAIN, null)
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER)
        val launchableApps = packageManager.queryIntentActivities(mainIntent, 0)
        
        return launchableApps
            .mapNotNull { resolveInfo ->
                try {
                    val packageName = resolveInfo.activityInfo.packageName
                    
                    // Exclude DoomShield itself
                    if (packageName == context.packageName) return@mapNotNull null
                    
                    val appInfo = packageManager.getApplicationInfo(packageName, 0)
                    val appName = packageManager.getApplicationLabel(appInfo).toString()
                    val icon = packageManager.getApplicationIcon(packageName)
                    
                    InstalledApp(
                        packageName = packageName,
                        appName = appName,
                        icon = icon
                    )
                } catch (e: Exception) {
                    null
                }
            }
            .distinctBy { it.packageName } // Remove duplicates if an app has multiple launcher activities
            .sortedBy { it.appName.lowercase() }
    }
}
