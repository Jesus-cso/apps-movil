package org.example.project

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.example.project.data.AuthApi
import org.example.project.data.RegisterRequest

@Composable
fun RegisterScreen(api: AuthApi, onSuccess: () -> Unit, onBack: () -> Unit) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var role by remember { mutableStateOf(Role.CLIENTE) }
    var loading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()

    Column(
        Modifier.fillMaxSize().safeContentPadding().padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Crear cuenta", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(24.dp))
        OutlinedTextField(
            value = username, onValueChange = { username = it },
            label = { Text("Usuario") }, singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(
            value = password, onValueChange = { password = it },
            label = { Text("Contraseña (mínimo 4)") }, singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(16.dp))
        Text("Tipo de cuenta", style = MaterialTheme.typography.titleSmall)
        listOf(Role.CLIENTE, Role.INSTRUCTOR).forEach { option ->
            Row(
                Modifier.fillMaxWidth().clickable { role = option },
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(selected = role == option, onClick = { role = option })
                Text(option.name.lowercase().replaceFirstChar { it.uppercase() })
            }
        }
        error?.let {
            Spacer(Modifier.height(8.dp))
            Text(it, color = MaterialTheme.colorScheme.error)
        }
        Spacer(Modifier.height(20.dp))
        Button(
            onClick = {
                if (username.isBlank() || password.length < 4) {
                    error = "Usuario vacío o contraseña menor a 4 caracteres"; return@Button
                }
                loading = true; error = null
                scope.launch {
                    api.register(RegisterRequest(username.trim(), password, role.name))
                        .onSuccess { onSuccess() }
                        .onFailure { error = it.message ?: "Sin conexión" }
                    loading = false
                }
            },
            enabled = !loading,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (loading) CircularProgressIndicator(Modifier.size(20.dp), strokeWidth = 2.dp)
            else Text("Registrarme")
        }
        TextButton(onClick = onBack) { Text("Volver") }
    }
}