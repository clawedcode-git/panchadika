package com.panchadika.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.panchadika.presentation.theme.AppColors

@Composable
fun PermissionScreen(
    onRequestPermissions: () -> Unit,
    onOpenSettings: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = AppColors.Background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = null,
                modifier = Modifier.size(80.dp),
                tint = AppColors.Primary
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Permissions Required",
                style = MaterialTheme.typography.headlineSmall,
                color = AppColors.OnBackground,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Panchadika needs access to your SMS messages, contacts, and phone state to function as your SMS app.\n\n" +
                        "Your data is never shared or transmitted — everything stays on your device.",
                style = MaterialTheme.typography.bodyMedium,
                color = AppColors.OnSurfaceVariant,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Required permissions:\n• Read & Send SMS\n• Receive SMS\n• Read Contacts\n• Phone State",
                style = MaterialTheme.typography.bodySmall,
                color = AppColors.OnSurfaceVariant,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = onRequestPermissions,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppColors.Primary,
                    contentColor = AppColors.OnPrimary
                )
            ) {
                Text(
                    text = "Grant Permissions",
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedButton(
                onClick = onOpenSettings,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = AppColors.Primary
                )
            ) {
                Text(
                    text = "Open App Settings",
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "If the button above doesn't work, open App Settings and enable all permissions manually.",
                style = MaterialTheme.typography.bodySmall,
                color = AppColors.OnSurfaceVariant,
                textAlign = TextAlign.Center
            )
        }
    }
}
