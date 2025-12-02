package com.example.doomshield.presentation.screens.wellbeing_hub

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.doomshield.ui.theme.DoomGradientEnd
import com.example.doomshield.ui.theme.DoomGradientStart

@Composable
fun WellbeingHubScreen(
    navController: NavController,
    viewModel: WellbeingViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(DoomGradientStart, DoomGradientEnd)
                )
            )
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Wellbeing Hub",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White,
            modifier = Modifier.padding(top = 32.dp, bottom = 24.dp)
        )

        // Breathing Exercise Section
        BreathingSection(
            isActive = uiState.isBreathingActive,
            phase = uiState.breathingPhase,
            text = uiState.breathingText,
            onToggle = { viewModel.toggleBreathing() }
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Focus Timer Section
        FocusTimerSection(
            isRunning = uiState.isFocusTimerRunning,
            timeRemaining = uiState.focusTimeRemainingSeconds,
            selectedDuration = uiState.selectedFocusDurationMinutes,
            onDurationSelect = { viewModel.setFocusDuration(it) },
            onToggle = { viewModel.toggleFocusTimer() }
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Mood Tracker Section
        MoodTrackerSection(
            selectedMood = uiState.selectedMood,
            onMoodSelect = { viewModel.selectMood(it) }
        )
        
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun BreathingSection(
    isActive: Boolean,
    phase: BreathingPhase,
    text: String,
    onToggle: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.1f))
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Breathing Exercise",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(24.dp))

            val scale by animateFloatAsState(
                targetValue = when (phase) {
                    BreathingPhase.INHALE -> 1.5f
                    BreathingPhase.HOLD -> 1.5f
                    BreathingPhase.EXHALE -> 1.0f
                    else -> 1.0f
                },
                animationSpec = tween(durationMillis = if (phase == BreathingPhase.INHALE) 4000 else 8000),
                label = "BreathingScale"
            )

            Box(
                modifier = Modifier
                    .size(120.dp)
                    .scale(scale)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = text,
                    color = Color.White,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = onToggle,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isActive) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
                )
            ) {
                Icon(
                    imageVector = if (isActive) Icons.Default.Stop else Icons.Default.PlayArrow,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(if (isActive) "Stop" else "Start")
            }
        }
    }
}

@Composable
fun FocusTimerSection(
    isRunning: Boolean,
    timeRemaining: Long,
    selectedDuration: Int,
    onDurationSelect: (Int) -> Unit,
    onToggle: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.1f))
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Focus Timer",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(16.dp))

            if (!isRunning) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf(15, 30, 45, 60).forEach { duration ->
                        FilterChip(
                            selected = selectedDuration == duration,
                            onClick = { onDurationSelect(duration) },
                            label = { Text("${duration}m") },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.primary,
                                selectedLabelColor = Color.White,
                                labelColor = Color.White
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = if (isRunning) formatTime(timeRemaining) else "${selectedDuration}:00",
                style = MaterialTheme.typography.displayMedium,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = onToggle,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isRunning) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
                )
            ) {
                Text(if (isRunning) "Stop Focus" else "Start Focus")
            }
        }
    }
}

@Composable
fun MoodTrackerSection(
    selectedMood: Mood?,
    onMoodSelect: (Mood) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.1f))
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "How are you feeling?",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Mood.values().forEach { mood ->
                    MoodItem(
                        mood = mood,
                        isSelected = selectedMood == mood,
                        onSelect = { onMoodSelect(mood) }
                    )
                }
            }
        }
    }
}

@Composable
fun MoodItem(mood: Mood, isSelected: Boolean, onSelect: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable { onSelect() }
            .padding(8.dp)
            .scale(if (isSelected) 1.2f else 1.0f)
    ) {
        Text(
            text = when (mood) {
                Mood.GREAT -> "🤩"
                Mood.GOOD -> "🙂"
                Mood.OKAY -> "😐"
                Mood.BAD -> "😔"
                Mood.TERRIBLE -> "😫"
            },
            fontSize = 32.sp
        )
    }
}

fun formatTime(seconds: Long): String {
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60
    return "%02d:%02d".format(minutes, remainingSeconds)
}