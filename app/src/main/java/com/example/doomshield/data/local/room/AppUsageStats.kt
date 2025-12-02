package com.example.doomshield.data.local.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "app_usage_stats")
data class AppUsageStats(
    @PrimaryKey val packageName: String,
    val appName: String? = null,
    val totalDuration: Long,
    val lastUsed: Long? = null
)
