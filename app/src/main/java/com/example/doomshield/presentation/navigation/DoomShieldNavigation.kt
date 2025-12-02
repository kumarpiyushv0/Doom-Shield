package com.example.doomshield.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.doomshield.presentation.screens.app_management.AppManagementScreen
import com.example.doomshield.presentation.screens.onboarding.OnboardingScreen
import com.example.doomshield.presentation.screens.permissions.PermissionsScreen
import com.example.doomshield.presentation.screens.home.HomeScreen
import com.example.doomshield.presentation.screens.wellbeing_hub.WellbeingHubScreen
import com.example.doomshield.presentation.screens.usage_stats.UsageStatsScreen
import com.example.doomshield.presentation.screens.settings.SettingsScreen

sealed class Screen(val route: String) {
    object Onboarding : Screen("onboarding_screen")
    object Permissions : Screen("permissions_screen")
    object Home : Screen("home_screen")
    object WellbeingHub : Screen("wellbeing_hub_screen")
    object UsageStats : Screen("usage_stats_screen")
    object Settings : Screen("settings_screen")
    object AppManagement : Screen("app_management_screen")
}

@Composable
fun DoomShieldNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Permissions.route,
        modifier = modifier
    ) {
        composable(Screen.Onboarding.route) {
            OnboardingScreen(navController = navController)
        }
        composable(Screen.Permissions.route) {
            PermissionsScreen(navController = navController)
        }
        composable(Screen.Home.route) {
            HomeScreen(navController = navController) // Now displays HomeScreen
        }
        composable(Screen.WellbeingHub.route) {
            WellbeingHubScreen(navController = navController)
        }
        composable(Screen.UsageStats.route) { // New composable
            UsageStatsScreen(navController = navController)
        }
        composable(Screen.Settings.route) {
            SettingsScreen(navController = navController)
        }
        composable(Screen.AppManagement.route) {
            AppManagementScreen(navController = navController)
        }
    }
}