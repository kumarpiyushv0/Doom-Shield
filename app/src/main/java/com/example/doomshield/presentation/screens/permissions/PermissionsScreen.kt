package com.example.doomshield.presentation.screens.permissions

import android.app.AppOpsManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Process
import android.provider.Settings
import android.text.TextUtils.SimpleStringSplitter
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavController
import com.example.doomshield.doomshield.DoomshieldAccessibilityService
import com.example.doomshield.presentation.navigation.Screen

@Composable
fun PermissionsScreen(navController: NavController) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    var isAccessibilityGranted by remember { mutableStateOf(isAccessibilityServiceEnabled(context)) }
    var isUsageGranted by remember { mutableStateOf(isUsageAccessGranted(context)) }
    var isOverlayGranted by remember { mutableStateOf(canDrawOverlays(context)) }

    // Refresh permissions when app resumes (returns from Settings)
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                isAccessibilityGranted = isAccessibilityServiceEnabled(context)
                isUsageGranted = isUsageAccessGranted(context)
                isOverlayGranted = canDrawOverlays(context)
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    val allGranted = isAccessibilityGranted && isUsageGranted && isOverlayGranted

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Permissions Required",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text(
            text = "Doom Shield needs a few permissions to protect you from doomscrolling:",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        PermissionButton(
            text = "Grant Accessibility Service",
            isGranted = isAccessibilityGranted,
            onClick = { openAccessibilitySettings(context) }
        )
        Spacer(modifier = Modifier.height(16.dp))
        PermissionButton(
            text = "Grant Usage Access",
            isGranted = isUsageGranted,
            onClick = { openUsageAccessSettings(context) }
        )
        Spacer(modifier = Modifier.height(16.dp))
        PermissionButton(
            text = "Grant Display Over Other Apps",
            isGranted = isOverlayGranted,
            onClick = { openOverlaySettings(context) }
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                if (allGranted) {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                    }
                }
            },
            enabled = allGranted
        ) {
            Text("Continue")
        }
    }
}

@Composable
fun PermissionButton(text: String, isGranted: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        enabled = !isGranted
    ) {
        Text(text + if (isGranted) " (Granted)" else " (Tap to Grant)")
    }
}

//region Permission Checkers and Intent Launchers
fun isAccessibilityServiceEnabled(context: Context): Boolean {
    val expectedComponentName = context.packageName + "/" + DoomshieldAccessibilityService::class.java.canonicalName
    val enabledServicesSetting = Settings.Secure.getString(
        context.contentResolver,
        Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES
    ) ?: return false

    val colonSplitter = SimpleStringSplitter(':')
    colonSplitter.setString(enabledServicesSetting)

    while (colonSplitter.hasNext()) {
        val componentName = colonSplitter.next()
        if (componentName.equals(expectedComponentName, ignoreCase = true)) {
            return true
        }
    }
    return false
}

fun openAccessibilitySettings(context: Context) {
    val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
    context.startActivity(intent)
}

fun isUsageAccessGranted(context: Context): Boolean {
    val appOps = context.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
    val mode = appOps.checkOpNoThrow(
        AppOpsManager.OPSTR_GET_USAGE_STATS,
        Process.myUid(),
        context.packageName
    )
    return mode == AppOpsManager.MODE_ALLOWED
}

fun openUsageAccessSettings(context: Context) {
    val intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
    context.startActivity(intent)
}

fun canDrawOverlays(context: Context): Boolean {
    return Settings.canDrawOverlays(context)
}

fun openOverlaySettings(context: Context) {
    val intent = Intent(
        Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
        Uri.parse("package:" + context.packageName)
    )
    context.startActivity(intent)
}
//endregion