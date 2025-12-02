package com.example.doomshield.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "doomscroll_events")
data class DoomscrollEvent(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val timestamp: Long,
    val packageName: String,
    val scrollDurationMillis: Long,
    val scrollCount: Int,
    val detectedReason: String // e.g., "excessive_scrolling", "long_session"
)