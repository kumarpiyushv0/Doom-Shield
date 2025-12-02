package com.example.doomshield.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface StatsDao {
    @Insert
    suspend fun insertSession(session: ScrollSession)

    @Query("SELECT * FROM scroll_sessions ORDER BY startTime DESC")
    fun getAllSessions(): Flow<List<ScrollSession>>

    @Query("SELECT * FROM scroll_sessions WHERE startTime >= :startTime ORDER BY startTime DESC")
    fun getSessionsSince(startTime: Long): Flow<List<ScrollSession>>
    
    @Query("SELECT SUM(endTime - startTime) FROM scroll_sessions WHERE startTime >= :startTime")
    fun getTotalUsageTimeSince(startTime: Long): Flow<Long?>

    @Query("SELECT packageName, SUM(endTime - startTime) as totalDuration FROM scroll_sessions WHERE startTime >= :startTime GROUP BY packageName ORDER BY totalDuration DESC")
    fun getUsageByPackage(startTime: Long): Flow<List<AppUsageStats>>
}


