package com.example.doomshield.presentation.screens.wellbeing_hub

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class BreathingPhase {
    IDLE, INHALE, HOLD, EXHALE
}

enum class Mood {
    GREAT, GOOD, OKAY, BAD, TERRIBLE
}

data class WellbeingUiState(
    // Breathing Exercise
    val isBreathingActive: Boolean = false,
    val breathingPhase: BreathingPhase = BreathingPhase.IDLE,
    val breathingText: String = "Start Breathing",
    
    // Focus Timer
    val isFocusTimerRunning: Boolean = false,
    val focusTimeRemainingSeconds: Long = 0,
    val selectedFocusDurationMinutes: Int = 15,
    
    // Mood Tracker
    val selectedMood: Mood? = null
)

@HiltViewModel
class WellbeingViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(WellbeingUiState())
    val uiState: StateFlow<WellbeingUiState> = _uiState.asStateFlow()

    private var breathingJob: Job? = null
    private var timerJob: Job? = null

    // --- Breathing Exercise Logic ---
    fun toggleBreathing() {
        if (_uiState.value.isBreathingActive) {
            stopBreathing()
        } else {
            startBreathing()
        }
    }

    private fun startBreathing() {
        _uiState.update { it.copy(isBreathingActive = true) }
        breathingJob = viewModelScope.launch {
            while (true) {
                // Inhale (4s)
                _uiState.update { it.copy(breathingPhase = BreathingPhase.INHALE, breathingText = "Inhale...") }
                delay(4000)
                
                // Hold (7s)
                _uiState.update { it.copy(breathingPhase = BreathingPhase.HOLD, breathingText = "Hold...") }
                delay(7000)
                
                // Exhale (8s)
                _uiState.update { it.copy(breathingPhase = BreathingPhase.EXHALE, breathingText = "Exhale...") }
                delay(8000)
            }
        }
    }

    private fun stopBreathing() {
        breathingJob?.cancel()
        _uiState.update { 
            it.copy(
                isBreathingActive = false, 
                breathingPhase = BreathingPhase.IDLE, 
                breathingText = "Start Breathing"
            ) 
        }
    }

    // --- Focus Timer Logic ---
    fun setFocusDuration(minutes: Int) {
        if (!_uiState.value.isFocusTimerRunning) {
            _uiState.update { it.copy(selectedFocusDurationMinutes = minutes) }
        }
    }

    fun toggleFocusTimer() {
        if (_uiState.value.isFocusTimerRunning) {
            stopFocusTimer()
        } else {
            startFocusTimer()
        }
    }

    private fun startFocusTimer() {
        val durationSeconds = _uiState.value.selectedFocusDurationMinutes * 60L
        _uiState.update { 
            it.copy(
                isFocusTimerRunning = true, 
                focusTimeRemainingSeconds = durationSeconds
            ) 
        }

        timerJob = viewModelScope.launch {
            while (_uiState.value.focusTimeRemainingSeconds > 0) {
                delay(1000)
                _uiState.update { 
                    it.copy(focusTimeRemainingSeconds = it.focusTimeRemainingSeconds - 1) 
                }
            }
            stopFocusTimer()
            // TODO: Trigger notification or sound when done
        }
    }

    private fun stopFocusTimer() {
        timerJob?.cancel()
        _uiState.update { it.copy(isFocusTimerRunning = false) }
    }

    // --- Mood Tracker Logic ---
    fun selectMood(mood: Mood) {
        _uiState.update { it.copy(selectedMood = mood) }
        // TODO: Save mood to database
    }
}
