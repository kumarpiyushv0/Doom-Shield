package com.example.doomshield.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.doomshield.MainActivity
import com.example.doomshield.R

class DoomShieldForegroundService : Service() {

    companion object {
        const val NOTIFICATION_CHANNEL_ID = "DoomShieldServiceChannel"
        const val NOTIFICATION_ID = 1
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        Log.d("DoomShieldService", "Foreground Service Created")
        createNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("DoomShieldService", "Foreground Service Started")
        val notification = createNotification()
        startForeground(NOTIFICATION_ID, notification)

        // TODO: Implement actual background tasks here
        // e.g., continuously monitoring accessibility events or usage stats

        return START_STICKY // Service will be restarted if killed by the system
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("DoomShieldService", "Foreground Service Destroyed")
        stopForeground(true) // Remove notification when service is stopped
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                "Doom Shield Service Channel",
                NotificationManager.IMPORTANCE_LOW
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)
        }
    }

    private fun createNotification(): Notification {
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            notificationIntent,
            PendingIntent.FLAG_IMMUTABLE // Use FLAG_IMMUTABLE with Android S+
        )

        return NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setContentTitle("Doom Shield Active")
            .setContentText("Protecting you from doomscrolling.")
            .setSmallIcon(R.drawable.ic_launcher_foreground) // Use a proper icon
            .setContentIntent(pendingIntent)
            .build()
    }
}