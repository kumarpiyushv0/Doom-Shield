package com.example.doomshield.domain.usecase

import com.example.doomshield.data.local.room.AppTimeLimit
import com.example.doomshield.data.local.room.AppUsageSession
import com.example.doomshield.domain.repository.AppTimeLimitRepository
import com.example.doomshield.domain.repository.AppUsageRepository
import javax.inject.Inject

class CheckAppBlockedUseCase @Inject constructor(
    private val appTimeLimitRepository: AppTimeLimitRepository,
    private val appUsageRepository: AppUsageRepository
) {
    suspend operator fun invoke(packageName: String, currentDate: String): BlockStatus {
        // Get time limit for this app
        val timeLimit = appTimeLimitRepository.getTimeLimitForAppSync(packageName)
            ?: return BlockStatus.NotLimited
        
        if (!timeLimit.isEnabled) {
            return BlockStatus.NotLimited
        }
        
        // Get today's usage for this app
        val todayUsageMillis = appUsageRepository.getTodayUsageForAppSync(packageName, currentDate) ?: 0L
        val todayUsageMinutes = todayUsageMillis / 60_000
        
        // Check if limit exceeded
        return if (todayUsageMinutes >= timeLimit.dailyLimitMinutes) {
            BlockStatus.Blocked(
                timeLimit = timeLimit,
                usedMinutes = todayUsageMinutes,
                remainingCooldownMinutes = timeLimit.cooldownMinutes
            )
        } else {
            BlockStatus.Active(
                timeLimit = timeLimit,
                usedMinutes = todayUsageMinutes,
                remainingMinutes = timeLimit.dailyLimitMinutes - todayUsageMinutes
            )
        }
    }
}

sealed class BlockStatus {
    object NotLimited : BlockStatus()
    
    data class Active(
        val timeLimit: AppTimeLimit,
        val usedMinutes: Long,
        val remainingMinutes: Long
    ) : BlockStatus()
    
    data class Blocked(
        val timeLimit: AppTimeLimit,
        val usedMinutes: Long,
        val remainingCooldownMinutes: Int
    ) : BlockStatus()
}
