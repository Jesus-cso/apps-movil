package org.example.project

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
private fun RoleScaffold(
    title: String,
    username: String,
    options: List<String>,
    onLogout: () -> Unit
) {
    Column(
        Modifier.fillMaxSize().safeContentPadding().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(title, style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(8.dp))
        Text("Sesión iniciada como $username")
        Spacer(Modifier.height(24.dp))
        options.forEach { option ->
            Card(Modifier.fillMaxWidth().padding(vertical = 6.dp)) {
                Text(option, Modifier.padding(16.dp), style = MaterialTheme.typography.titleMedium)
            }
        }
        Spacer(Modifier.weight(1f))
        Button(onClick = onLogout) { Text("Cerrar sesión") }
    }
}

@Composable
fun AdminScreen(username: String, onLogout: () -> Unit) =
    RoleScaffold("Panel de administrador", username,
        listOf("Usuarios", "Instructores", "Configuración"), onLogout)

@Composable
fun InstructorScreen(username: String, onLogout: () -> Unit) =
    RoleScaffold("Panel de instructor", username,
        listOf("Mis clientes", "Mi agenda", "Seguimiento"), onLogout)

@Composable
fun ClienteScreen(username: String, onLogout: () -> Unit) =
    RoleScaffold("Mi cuenta", username,
        listOf("Mi perfil", "Mis actividades", "Ayuda"), onLogout)