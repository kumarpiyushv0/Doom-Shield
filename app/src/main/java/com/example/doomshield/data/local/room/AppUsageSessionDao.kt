package com.example.doomshield.data.local.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow

/**
 * DAO for managing app usage sessions.
 */
@Dao
interface AppUsageSessionDao {
    
    @Insert
    suspend fun insertSession(session: AppUsageSession)
    
    @Query("SELECT * FROM app_usage_sessions WHERE packageName = :packageName ORDER BY startTime DESC")
    fun getSessionsForApp(packageName: String): Flow<List<AppUsageSession>>
    
    @Query("SELECT * FROM app_usage_sessions WHERE date = :date ORDER BY startTime DESC")
    fun getSessionsByDate(date: String): Flow<List<AppUsageSession>>
    
    @Query("SELECT SUM(durationMillis) FROM app_usage_sessions WHERE packageName = :packageName AND date = :date")
    fun getTotalUsageForAppOnDate(packageName: String, date: String): Flow<Long?>
    
    @Query("SELECT SUM(durationMillis) FROM app_usage_sessions WHERE packageName = :packageName AND date = :date")
    suspend fun getTotalUsageForAppOnDateSync(packageName: String, date: String): Long?
    
    @Query("SELECT COUNT(*) FROM app_usage_sessions WHERE packageName = :packageName AND date = :date")
    fun getTotalOpensForAppOnDate(packageName: String, date: String): Flow<Int>
    
    @Query("SELECT AVG(durationMillis) FROM app_usage_sessions WHERE packageName = :packageName AND date = :date")
    fun getAverageSessionDuration(packageName: String, date: String): Flow<Long?>
    
    @Query("DELETE FROM app_usage_sessions WHERE date < :cutoffDate")
    suspend fun deleteOldSessions(cutoffDate: String)
    
    @Query("SELECT * FROM app_usage_sessions ORDER BY startTime DESC LIMIT :limit")
    fun getRecentSessions(limit: Int): Flow<List<AppUsageSession>>
}
