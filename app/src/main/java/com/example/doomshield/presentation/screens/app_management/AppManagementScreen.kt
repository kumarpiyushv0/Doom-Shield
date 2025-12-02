package com.example.doomshield.presentation.screens.app_management

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.doomshield.domain.model.InstalledApp
import com.example.doomshield.ui.theme.DoomGradientEnd
import com.example.doomshield.ui.theme.DoomGradientStart

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppManagementScreen(
    navController: NavController,
    viewModel: AppManagementViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var selectedApp by remember { mutableStateOf<InstalledApp?>(null) }
    var showTimeLimitDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(DoomGradientStart, DoomGradientEnd)
                )
            )
            .padding(16.dp)
    ) {
        Text(
            text = "App Time Limits",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White,
            modifier = Modifier.padding(top = 32.dp, bottom = 16.dp)
        )

        Text(
            text = "Select apps to set daily usage limits",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White.copy(alpha = 0.8f),
            modifier = Modifier.padding(bottom = 24.dp)
        )

        if (uiState.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color.White)
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(uiState.installedApps) { app ->
                    val hasLimit = uiState.appLimits.containsKey(app.packageName)
                    val timeLimit = uiState.appLimits[app.packageName]

                    AppListItem(
                        app = app,
                        hasLimit = hasLimit,
                        timeLimit = timeLimit?.dailyLimitMinutes,
                        isEnabled = timeLimit?.isEnabled ?: false,
                        onClick = {
                            selectedApp = app
                            showTimeLimitDialog = true
                        },
                        onToggle = { enabled ->
                            viewModel.toggleTimeLimit(app.packageName, enabled)
                        },
                        onRemove = {
                            viewModel.removeTimeLimit(app.packageName)
                        }
                    )
                }
            }
        }
    }

    if (showTimeLimitDialog && selectedApp != null) {
        TimeLimitDialog(
            app = selectedApp!!,
            existingLimit = uiState.appLimits[selectedApp!!.packageName],
            onDismiss = { showTimeLimitDialog = false },
            onConfirm = { dailyLimit, cooldown ->
                viewModel.setTimeLimit(
                    selectedApp!!.packageName,
                    selectedApp!!.appName,
                    dailyLimit,
                    cooldown
                )
                showTimeLimitDialog = false
            }
        )
    }
}

@Composable
fun AppListItem(
    app: InstalledApp,
    hasLimit: Boolean,
    timeLimit: Int?,
    isEnabled: Boolean,
    onClick: () -> Unit,
    onToggle: (Boolean) -> Unit,
    onRemove: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (hasLimit && isEnabled) 
                MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
            else 
                Color.White.copy(alpha = 0.1f)
        )
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                app.icon?.let { drawable ->
                    Image(
                        bitmap = drawable.toBitmap(48, 48).asImageBitmap(),
                        contentDescription = null,
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                    )
                }
                
                Spacer(modifier = Modifier.width(12.dp))
                
                Column {
                    Text(
                        text = app.appName,
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    if (hasLimit && timeLimit != null) {
                        Text(
                            text = "$timeLimit min/day",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.White.copy(alpha = 0.7f)
                        )
                    } else {
                        Text(
                            text = "No limit set",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.White.copy(alpha = 0.5f)
                        )
                    }
                }
            }

            if (hasLimit) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Switch(
                        checked = isEnabled,
                        onCheckedChange = onToggle
                    )
                    IconButton(onClick = onRemove) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Remove limit",
                            tint = Color.White.copy(alpha = 0.7f)
                        )
                    }
                }
            } else {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add limit",
                    tint = Color.White.copy(alpha = 0.5f)
                )
            }
        }
    }
}

@Composable
fun TimeLimitDialog(
    app: InstalledApp,
    existingLimit: com.example.doomshield.data.local.room.AppTimeLimit?,
    onDismiss: () -> Unit,
    onConfirm: (dailyLimit: Int, cooldown: Int) -> Unit
) {
    var dailyLimit by remember { mutableStateOf(existingLimit?.dailyLimitMinutes?.toString() ?: "60") }
    var cooldown by remember { mutableStateOf(existingLimit?.cooldownMinutes?.toString() ?: "30") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text("Set Time Limit for ${app.appName}")
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                OutlinedTextField(
                    value = dailyLimit,
                    onValueChange = { dailyLimit = it },
                    label = { Text("Daily Limit (minutes)") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = cooldown,
                    onValueChange = { cooldown = it },
                    label = { Text("Cooldown Period (minutes)") },
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = "After using this app for the daily limit, it will be blocked for the cooldown period.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val daily = dailyLimit.toIntOrNull() ?: 60
                    val cool = cooldown.toIntOrNull() ?: 30
                    onConfirm(daily, cool)
                }
            ) {
                Icon(Icons.Default.Check, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
