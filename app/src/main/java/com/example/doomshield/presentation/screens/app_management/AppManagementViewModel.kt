package com.example.doomshield.presentation.screens.app_management

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.doomshield.data.local.room.AppTimeLimit
import com.example.doomshield.domain.model.InstalledApp
import com.example.doomshield.domain.repository.AppTimeLimitRepository
import com.example.doomshield.domain.usecase.GetInstalledAppsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AppManagementUiState(
    val installedApps: List<InstalledApp> = emptyList(),
    val appLimits: Map<String, AppTimeLimit> = emptyMap(),
    val isLoading: Boolean = true,
    val searchQuery: String = ""
)

@HiltViewModel
class AppManagementViewModel @Inject constructor(
    private val getInstalledAppsUseCase: GetInstalledAppsUseCase,
    private val appTimeLimitRepository: AppTimeLimitRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AppManagementUiState())
    val uiState: StateFlow<AppManagementUiState> = _uiState.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            try {
                Log.d("AppManagement", "Starting to load apps...")
                _uiState.update { it.copy(isLoading = true) }
                
                // Load installed apps
                val apps = getInstalledAppsUseCase()
                Log.d("AppManagement", "Loaded ${apps.size} apps")
                apps.take(5).forEach { app ->
                    Log.d("AppManagement", "App: ${app.appName} (${app.packageName})")
                }
                
                // Load existing time limits
                appTimeLimitRepository.getAllTimeLimits().collect { limits: List<AppTimeLimit> ->
                    val limitsMap = limits.associateBy { it.packageName }
                    Log.d("AppManagement", "Loaded ${limitsMap.size} time limits")
                    
                    _uiState.update {
                        it.copy(
                            installedApps = apps,
                            appLimits = limitsMap,
                            isLoading = false
                        )
                    }
                    Log.d("AppManagement", "UI State updated with ${apps.size} apps")
                }
            } catch (e: Exception) {
                Log.e("AppManagement", "Error loading apps", e)
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun setTimeLimit(packageName: String, appName: String, dailyLimitMinutes: Int, cooldownMinutes: Int) {
        viewModelScope.launch {
            val timeLimit = AppTimeLimit(
                packageName = packageName,
                appName = appName,
                dailyLimitMinutes = dailyLimitMinutes,
                cooldownMinutes = cooldownMinutes,
                isEnabled = true
            )
            appTimeLimitRepository.setTimeLimit(timeLimit)
        }
    }

    fun removeTimeLimit(packageName: String) {
        viewModelScope.launch {
            appTimeLimitRepository.removeTimeLimit(packageName)
        }
    }

    fun toggleTimeLimit(packageName: String, enabled: Boolean) {
        viewModelScope.launch {
            val existing = _uiState.value.appLimits[packageName]
            if (existing != null) {
                appTimeLimitRepository.updateTimeLimit(existing.copy(isEnabled = enabled))
            }
        }
    }

    fun updateSearchQuery(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }
}
