package com.example.doomshield.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@Singleton
class UserPreferencesRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val IS_DARK_MODE = booleanPreferencesKey("is_dark_mode")
    private val IS_SHIELD_ENABLED = booleanPreferencesKey("is_shield_enabled")
    private val TOTAL_INTERVENTIONS = intPreferencesKey("total_interventions")
    private val TOTAL_TIME_SAVED_MINUTES = longPreferencesKey("total_time_saved_minutes")

    val isDarkMode: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[IS_DARK_MODE] ?: true // Default to true (Dark Mode)
        }

    val isShieldEnabled: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[IS_SHIELD_ENABLED] ?: true // Default to true (Enabled)
        }

    val totalInterventions: Flow<Int> = context.dataStore.data
        .map { preferences ->
            preferences[TOTAL_INTERVENTIONS] ?: 0
        }

    val totalTimeSavedMinutes: Flow<Long> = context.dataStore.data
        .map { preferences ->
            preferences[TOTAL_TIME_SAVED_MINUTES] ?: 0L
        }

    suspend fun setDarkMode(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[IS_DARK_MODE] = enabled
        }
    }

    suspend fun setShieldEnabled(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[IS_SHIELD_ENABLED] = enabled
        }
    }

    suspend fun incrementInterventions() {
        context.dataStore.edit { preferences ->
            val current = preferences[TOTAL_INTERVENTIONS] ?: 0
            preferences[TOTAL_INTERVENTIONS] = current + 1
        }
    }

    suspend fun addTimeSaved(minutes: Long) {
        context.dataStore.edit { preferences ->
            val current = preferences[TOTAL_TIME_SAVED_MINUTES] ?: 0L
            preferences[TOTAL_TIME_SAVED_MINUTES] = current + minutes
        }
    }
}
