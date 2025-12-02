package com.example.doomshield.domain.repository

import com.example.doomshield.data.local.room.AppUsageSession
import kotlinx.coroutines.flow.Flow

interface AppUsageRepository {
    suspend fun logSession(session: AppUsageSession)
    fun getTodayUsageForApp(packageName: String, date: String): Flow<Long?>
    suspend fun getTodayUsageForAppSync(packageName: String, date: String): Long?
    fun getUsageByDate(date: String): Flow<List<AppUsageSession>>
    fun getTotalOpensToday(packageName: String, date: String): Flow<Int>
    fun getSessionsForApp(packageName: String): Flow<List<AppUsageSession>>
    fun getRecentSessions(limit: Int): Flow<List<AppUsageSession>>
    suspend fun deleteOldSessions(cutoffDate: String)
}
