package com.example.doomshield

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme // Missing import
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.doomshield.presentation.navigation.DoomShieldNavHost
import com.example.doomshield.ui.theme.DoomShieldTheme
import dagger.hilt.android.AndroidEntryPoint

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.doomshield.data.local.datastore.UserPreferencesRepository
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var userPreferencesRepository: UserPreferencesRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val isDarkMode by userPreferencesRepository.isDarkMode.collectAsState(initial = true)
            
            DoomShieldTheme(darkTheme = isDarkMode) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // AppNavigation() is assumed to be a composable that encapsulates the navigation logic
                    // For this example, we'll keep the original DoomShieldNavHost call,
                    // but if AppNavigation() is defined elsewhere, it should be used.
                    val navController = rememberNavController()
                    DoomShieldNavHost(navController = navController)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DoomShieldTheme {
        // Preview the NavHost with a specific start destination for demonstration
        val navController = rememberNavController()
        DoomShieldNavHost(navController = navController)
    }
}