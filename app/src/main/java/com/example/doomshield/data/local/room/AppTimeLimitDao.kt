package com.example.doomshield.data.local.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow

/**
 * DAO for managing app time limits.
 */
@Dao
interface AppTimeLimitDao {
    
    @Query("SELECT * FROM app_time_limits WHERE isEnabled = 1")
    fun getAllEnabledLimits(): Flow<List<AppTimeLimit>>
    
    @Query("SELECT * FROM app_time_limits")
    fun getAllLimits(): Flow<List<AppTimeLimit>>
    
    @Query("SELECT * FROM app_time_limits WHERE packageName = :packageName")
    fun getTimeLimitForApp(packageName: String): Flow<AppTimeLimit?>
    
    @Query("SELECT * FROM app_time_limits WHERE packageName = :packageName")
    suspend fun getTimeLimitForAppSync(packageName: String): AppTimeLimit?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTimeLimit(timeLimit: AppTimeLimit)
    
    @Update
    suspend fun updateTimeLimit(timeLimit: AppTimeLimit)
    
    @Delete
    suspend fun deleteTimeLimit(timeLimit: AppTimeLimit)
    
    @Query("DELETE FROM app_time_limits WHERE packageName = :packageName")
    suspend fun deleteTimeLimitByPackage(packageName: String)
}
