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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.panchadika.presentation.theme.PanchadikaTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow

object PanchadikaNavControllerHolder {
    var navController: NavHostController? = null
    var pendingAddress: String? = null
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val _permissionsGranted = MutableStateFlow(false)

    private val corePermissions = arrayOf(
        Manifest.permission.READ_SMS,
        Manifest.permission.SEND_SMS,
        Manifest.permission.RECEIVE_SMS
    )

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
    ) { _ ->
        _permissionsGranted.value = areCorePermissionsGranted()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        _permissionsGranted.value = areCorePermissionsGranted()
        if (!_permissionsGranted.value) {
            permissionLauncher.launch(requiredPermissions)
        }

        setContent {
            val permissionsGranted by _permissionsGranted.collectAsState()
            PanchadikaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    if (permissionsGranted) {
                        PanchadikaAppWithBackHandling()
                    } else {
                        PermissionScreen(
                            onRequestPermissions = { requestAllPermissions() },
                            onOpenSettings = { openAppSettings() }
                        )
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        _permissionsGranted.value = areCorePermissionsGranted()
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIntent(intent)
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

    private fun areCorePermissionsGranted(): Boolean {
        return corePermissions.all { permission ->
            ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
        }
    }

    private fun requestAllPermissions() {
        val toRequest = requiredPermissions.filter { permission ->
            ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED
        }.toTypedArray()

        if (toRequest.isNotEmpty()) {
            permissionLauncher.launch(toRequest)
        } else {
            _permissionsGranted.value = true
        }
    }

    private fun openAppSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts("package", packageName, null)
        }
        startActivity(intent)
    }
}

@Composable
fun PanchadikaAppWithBackHandling() {
    val navController = rememberNavController()
    remember { PanchadikaNavControllerHolder.navController = navController }

    PanchadikaNavHost(navController = navController)
}
