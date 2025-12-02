package com.example.doomshield.domain.repository

import com.example.doomshield.data.local.room.AppTimeLimit
import kotlinx.coroutines.flow.Flow

interface AppTimeLimitRepository {
    fun getAllTimeLimits(): Flow<List<AppTimeLimit>>
    fun getAllEnabledLimits(): Flow<List<AppTimeLimit>>
    fun getTimeLimitForApp(packageName: String): Flow<AppTimeLimit?>
    suspend fun getTimeLimitForAppSync(packageName: String): AppTimeLimit?
    suspend fun setTimeLimit(appTimeLimit: AppTimeLimit)
    suspend fun updateTimeLimit(appTimeLimit: AppTimeLimit)
    suspend fun removeTimeLimit(packageName: String)
}
