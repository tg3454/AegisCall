package com.codefreaks.aegiscall.ui.guardian

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun GuardianHomeScreen(
    onViewAlert: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        Text(
            text = "AegisCall",
            style = MaterialTheme.typography.headlineLarge
        )

        Text(
            text = "Guardian Dashboard",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(top = 8.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {

                Text(
                    text = "Protected User",
                    style = MaterialTheme.typography.titleLarge
                )

                Text(
                    text = "🟢 Protected",
                    modifier = Modifier.padding(top = 8.dp)
                )

                Text(
                    text = "No active threats",
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Recent Activity",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            onClick = onViewAlert,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("VIEW LIVE MONITOR")
        }
    }
}