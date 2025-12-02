package com.example.doomshield.data.repository

import com.example.doomshield.data.local.room.AppTimeLimit
import com.example.doomshield.data.local.room.AppTimeLimitDao
import com.example.doomshield.domain.repository.AppTimeLimitRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppTimeLimitRepositoryImpl @Inject constructor(
    private val appTimeLimitDao: AppTimeLimitDao
) : AppTimeLimitRepository {
    
    override fun getAllTimeLimits(): Flow<List<AppTimeLimit>> {
        return appTimeLimitDao.getAllLimits()
    }
    
    override fun getAllEnabledLimits(): Flow<List<AppTimeLimit>> {
        return appTimeLimitDao.getAllEnabledLimits()
    }
    
    override fun getTimeLimitForApp(packageName: String): Flow<AppTimeLimit?> {
        return appTimeLimitDao.getTimeLimitForApp(packageName)
    }
    
    override suspend fun getTimeLimitForAppSync(packageName: String): AppTimeLimit? {
        return appTimeLimitDao.getTimeLimitForAppSync(packageName)
    }
    
    override suspend fun setTimeLimit(appTimeLimit: AppTimeLimit) {
        appTimeLimitDao.insertTimeLimit(appTimeLimit)
    }
    
    override suspend fun updateTimeLimit(appTimeLimit: AppTimeLimit) {
        appTimeLimitDao.updateTimeLimit(appTimeLimit.copy(updatedAt = System.currentTimeMillis()))
    }
    
    override suspend fun removeTimeLimit(packageName: String) {
        appTimeLimitDao.deleteTimeLimitByPackage(packageName)
    }
}
