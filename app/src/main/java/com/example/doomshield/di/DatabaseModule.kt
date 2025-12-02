package com.example.doomshield.di

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.doomshield.data.local.room.AppDatabase
import com.example.doomshield.data.local.room.AppTimeLimitDao
import com.example.doomshield.data.local.room.AppUsageSessionDao
import com.example.doomshield.data.local.room.StatsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    private val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            // Create app_time_limits table
            database.execSQL(
                """
                CREATE TABLE IF NOT EXISTS app_time_limits (
                    packageName TEXT PRIMARY KEY NOT NULL,
                    appName TEXT NOT NULL,
                    dailyLimitMinutes INTEGER NOT NULL,
                    cooldownMinutes INTEGER NOT NULL,
                    isEnabled INTEGER NOT NULL DEFAULT 1,
                    createdAt INTEGER NOT NULL,
                    updatedAt INTEGER NOT NULL
                )
                """.trimIndent()
            )
            
            // Create app_usage_sessions table
            database.execSQL(
                """
                CREATE TABLE IF NOT EXISTS app_usage_sessions (
                    id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                    packageName TEXT NOT NULL,
                    appName TEXT NOT NULL,
                    startTime INTEGER NOT NULL,
                    endTime INTEGER NOT NULL,
                    durationMillis INTEGER NOT NULL,
                    date TEXT NOT NULL
                )
                """.trimIndent()
            )
        }
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "doomshield_db"
        )
        .addMigrations(MIGRATION_1_2)
        .build()
    }

    @Provides
    fun provideStatsDao(database: AppDatabase): StatsDao {
        return database.statsDao()
    }
    
    @Provides
    fun provideAppTimeLimitDao(database: AppDatabase): AppTimeLimitDao {
        return database.appTimeLimitDao()
    }
    
    @Provides
    fun provideAppUsageSessionDao(database: AppDatabase): AppUsageSessionDao {
        return database.appUsageSessionDao()
    }
}