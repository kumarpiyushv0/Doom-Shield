package com.example.doomshield.presentation.screens.stats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.doomshield.data.local.datastore.UserPreferencesRepository
import com.example.doomshield.data.local.room.AppUsageStats
import com.example.doomshield.data.local.room.ScrollSession
import com.example.doomshield.domain.repository.StatsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class UsageStatsViewModel @Inject constructor(
    private val statsRepository: StatsRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    val weeklyStats: StateFlow<List<ScrollSession>> = statsRepository.getWeeklyStats()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val totalUsageToday: StateFlow<String> = statsRepository.getTotalUsageTimeToday()
        .map { millis ->
            if (millis == null) "0m"
            else {
                val minutes = millis / 60000
                "${minutes}m"
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = "0m"
        )

    val appUsageBreakdown: StateFlow<List<AppUsageStats>> = statsRepository.getUsageByPackageToday()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val totalInterventions: StateFlow<Int> = userPreferencesRepository.totalInterventions
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0
        )

    val totalTimeSavedMinutes: StateFlow<Long> = userPreferencesRepository.totalTimeSavedMinutes
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0L
        )
}
