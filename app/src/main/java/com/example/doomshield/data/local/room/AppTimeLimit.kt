package com.example.doomshield.data.local.room

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity representing a time limit configuration for a specific app.
 * Stores the daily usage limit and cooldown period for blocked apps.
 */
@Entity(tableName = "app_time_limits")
data class AppTimeLimit(
    @PrimaryKey val packageName: String,
    val appName: String,
    val dailyLimitMinutes: Int,
    val cooldownMinutes: Int,
    val isEnabled: Boolean = true,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)
