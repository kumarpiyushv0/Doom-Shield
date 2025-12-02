package com.example.doomshield.domain.repository

import com.example.doomshield.data.local.room.AppUsageStats
import com.example.doomshield.data.local.room.ScrollSession
import com.example.doomshield.data.local.room.StatsDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

interface StatsRepository {
    suspend fun logSession(packageName: String, startTime: Long, endTime: Long, scrollCount: Int)
    fun getWeeklyStats(): Flow<List<ScrollSession>>
    fun getTotalUsageTimeToday(): Flow<Long?>
    fun getUsageByPackageToday(): Flow<List<AppUsageStats>>
}

@Singleton
class StatsRepositoryImpl @Inject constructor(
    private val statsDao: StatsDao
) : StatsRepository {

    override suspend fun logSession(packageName: String, startTime: Long, endTime: Long, scrollCount: Int) {
        val session = ScrollSession(
            packageName = packageName,
            startTime = startTime,
            endTime = endTime,
            scrollCount = scrollCount
        )
        statsDao.insertSession(session)
    }

    override fun getWeeklyStats(): Flow<List<ScrollSession>> {
        val oneWeekAgo = System.currentTimeMillis() - (7 * 24 * 60 * 60 * 1000)
        return statsDao.getSessionsSince(oneWeekAgo)
    }
    
    override fun getTotalUsageTimeToday(): Flow<Long?> {
        // Start of today (simplified for now, ideally use Calendar/LocalDate)
        val startOfToday = System.currentTimeMillis() - (24 * 60 * 60 * 1000) // Rough approximation for demo
        return statsDao.getTotalUsageTimeSince(startOfToday)
    }

    override fun getUsageByPackageToday(): Flow<List<AppUsageStats>> {
        val startOfToday = System.currentTimeMillis() - (24 * 60 * 60 * 1000)
        return statsDao.getUsageByPackage(startOfToday)
    }
}
