package com.panchadika.presentation

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.panchadika.presentation.theme.PanchadikaTheme
import dagger.hilt.android.AndroidEntryPoint

object PanchadikaNavControllerHolder {
    var navController: NavHostController? = null
    var pendingAddress: String? = null
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val requiredPermissions = buildList {
        add(Manifest.permission.READ_SMS)
        add(Manifest.permission.SEND_SMS)
        add(Manifest.permission.RECEIVE_SMS)
        add(Manifest.permission.READ_CONTACTS)
        add(Manifest.permission.READ_PHONE_STATE)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            add(Manifest.permission.POST_NOTIFICATIONS)
            add(Manifest.permission.READ_PHONE_NUMBERS)
        }
    }.toTypedArray()

    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val allGranted = permissions.all { it.value }
        if (!allGranted) {
            val deniedPermissions = permissions.filter { !it.value }.keys
            if (deniedPermissions.any { it.contains("SMS") }) {
                showPermissionRationale()
            }
        }
    }

    private fun showPermissionRationale() {
        android.app.AlertDialog.Builder(this)
            .setTitle("SMS Permission Required")
            .setMessage("Panchadika needs SMS permissions to send and receive messages. Please grant the permissions in Settings.")
            .setPositiveButton("Open Settings") { _, _ ->
                openAppSettings()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun openAppSettings() {
        Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts("package", packageName, null)
            startActivity(this)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        checkAndRequestPermissions()

        setContent {
            PanchadikaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PanchadikaAppWithBackHandling()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        checkPermissionsAndShowRationaleIfNeeded()
    }

    private fun checkPermissionsAndShowRationaleIfNeeded() {
        val missingPermissions = requiredPermissions.filter { permission ->
            ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED
        }
        
        if (missingPermissions.isNotEmpty()) {
            val hasSmsPermission = missingPermissions.any { it.contains("SMS") }
            if (hasSmsPermission && shouldShowRequestPermissionRationale(Manifest.permission.READ_SMS)) {
                showPermissionRationale()
            }
        }
    }

    private fun handleIntent(intent: Intent?) {
        if (intent == null) return
        
        when (intent.action) {
            Intent.ACTION_VIEW, Intent.ACTION_SENDTO, Intent.ACTION_SEND -> {
                intent.data?.let { uri ->
                    when (uri.scheme) {
                        "sms", "smsto", "mmsto" -> {
                            val address = uri.schemeSpecificPart?.substringBefore("?") 
                                ?: uri.host
                            PanchadikaNavControllerHolder.pendingAddress = address
                        }
                    }
                }
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        val navController = PanchadikaNavControllerHolder.navController
        if (navController != null && navController.canGoBack()) {
            navController.popBackStack()
        } else {
            super.onBackPressed()
        }
    }

    private fun checkAndRequestPermissions() {
        val permissionsToRequest = requiredPermissions.filter { permission ->
            ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED
        }.toTypedArray()

        if (permissionsToRequest.isNotEmpty()) {
            permissionLauncher.launch(permissionsToRequest)
        }
    }
}

@Composable
fun PanchadikaAppWithBackHandling() {
    val navController = rememberNavController()
    remember { PanchadikaNavControllerHolder.navController = navController }
    
    PanchadikaNavHost(navController = navController)
}