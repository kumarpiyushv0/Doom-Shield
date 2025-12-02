package com.example.doomshield.data.repository

import com.example.doomshield.data.local.room.AppUsageSession
import com.example.doomshield.data.local.room.AppUsageSessionDao
import com.example.doomshield.domain.repository.AppUsageRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppUsageRepositoryImpl @Inject constructor(
    private val appUsageSessionDao: AppUsageSessionDao
) : AppUsageRepository {
    
    override suspend fun logSession(session: AppUsageSession) {
        appUsageSessionDao.insertSession(session)
    }
    
    override fun getTodayUsageForApp(packageName: String, date: String): Flow<Long?> {
        return appUsageSessionDao.getTotalUsageForAppOnDate(packageName, date)
    }
    
    override suspend fun getTodayUsageForAppSync(packageName: String, date: String): Long? {
        return appUsageSessionDao.getTotalUsageForAppOnDateSync(packageName, date)
    }
    
    override fun getUsageByDate(date: String): Flow<List<AppUsageSession>> {
        return appUsageSessionDao.getSessionsByDate(date)
    }
    
    override fun getTotalOpensToday(packageName: String, date: String): Flow<Int> {
        return appUsageSessionDao.getTotalOpensForAppOnDate(packageName, date)
    }
    
    override fun getSessionsForApp(packageName: String): Flow<List<AppUsageSession>> {
        return appUsageSessionDao.getSessionsForApp(packageName)
    }
    
    override fun getRecentSessions(limit: Int): Flow<List<AppUsageSession>> {
        return appUsageSessionDao.getRecentSessions(limit)
    }
    
    override suspend fun deleteOldSessions(cutoffDate: String) {
        appUsageSessionDao.deleteOldSessions(cutoffDate)
    }
}
