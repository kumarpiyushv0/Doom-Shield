package com.example.doomshield.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        ScrollSession::class,
        AppUsageStats::class,
        AppTimeLimit::class,
        AppUsageSession::class
    ],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun statsDao(): StatsDao
    abstract fun appTimeLimitDao(): AppTimeLimitDao
    abstract fun appUsageSessionDao(): AppUsageSessionDao
}
