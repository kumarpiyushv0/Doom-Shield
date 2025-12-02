package com.example.doomshield.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.doomshield.data.local.dao.DoomscrollEventDao
import com.example.doomshield.data.local.entity.DoomscrollEvent

@Database(
    entities = [DoomscrollEvent::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun doomscrollEventDao(): DoomscrollEventDao
}