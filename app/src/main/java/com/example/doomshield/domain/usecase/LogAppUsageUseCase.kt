package com.example.doomshield.domain.usecase

import com.example.doomshield.data.local.room.AppUsageSession
import com.example.doomshield.domain.repository.AppUsageRepository
import javax.inject.Inject

class LogAppUsageUseCase @Inject constructor(
    private val appUsageRepository: AppUsageRepository
) {
    suspend operator fun invoke(
        packageName: String,
        appName: String,
        startTime: Long,
        endTime: Long
    ) {
        val duration = endTime - startTime
        if (duration > 0) {
            val session = AppUsageSession(
                packageName = packageName,
                appName = appName,
                startTime = startTime,
                endTime = endTime,
                durationMillis = duration,
                date = AppUsageSession.getDateString(startTime)
            )
            appUsageRepository.logSession(session)
        }
    }
}
