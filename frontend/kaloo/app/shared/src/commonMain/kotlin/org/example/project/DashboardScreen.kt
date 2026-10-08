package org.example.project

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun DashboardScreen(email: String, role: String?, onLogout: () -> Unit) {
    Column(
        Modifier.fillMaxSize().safeContentPadding().padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Dashboard", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(16.dp))
        Text("Sesión iniciada como $email")
        role?.let { Text("Rol: $it") }
        Spacer(Modifier.height(24.dp))
        Button(onClick = onLogout) { Text("Cerrar sesión") }
    }
}

@Preview
@Composable
private fun DashboardScreenPreview() {
    MaterialTheme {
        DashboardScreen(email = "demo@kaloo.com", role = "CLIENTE", onLogout = {})
    }
}