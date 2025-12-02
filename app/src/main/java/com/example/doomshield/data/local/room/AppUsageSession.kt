package com.example.doomshield.data.local.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.*

/**
 * Entity representing a single app usage session.
 * Tracks when an app was opened, closed, and the duration.
 */
@Entity(tableName = "app_usage_sessions")
data class AppUsageSession(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val packageName: String,
    val appName: String,
    val startTime: Long,
    val endTime: Long,
    val durationMillis: Long,
    val date: String // YYYY-MM-DD format for daily grouping
) {
    companion object {
        fun getDateString(timestamp: Long): String {
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            return sdf.format(Date(timestamp))
        }
    }
}
