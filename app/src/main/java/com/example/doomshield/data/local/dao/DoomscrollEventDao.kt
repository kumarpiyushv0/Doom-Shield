package com.example.doomshield.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.doomshield.data.local.entity.DoomscrollEvent
import kotlinx.coroutines.flow.Flow

@Dao
interface DoomscrollEventDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDoomscrollEvent(event: DoomscrollEvent)

    @Query("SELECT * FROM doomscroll_events ORDER BY timestamp DESC")
    fun getAllDoomscrollEvents(): Flow<List<DoomscrollEvent>>

    @Query("SELECT * FROM doomscroll_events WHERE timestamp >= :startTime ORDER BY timestamp DESC")
    fun getDoomscrollEventsSince(startTime: Long): Flow<List<DoomscrollEvent>>

    @Query("DELETE FROM doomscroll_events")
    suspend fun deleteAllDoomscrollEvents()
}