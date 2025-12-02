package com.example.doomshield.data.local.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "scroll_sessions")
data class ScrollSession(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val packageName: String,
    val startTime: Long,
    val endTime: Long,
    val scrollCount: Int
)
