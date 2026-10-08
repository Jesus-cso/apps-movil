package org.example.project

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import org.example.project.data.AuthApi
import org.example.project.data.baseUrl
import org.example.project.data.createHttpClient

@Composable
fun App() {
    MaterialTheme {
        val api = remember { AuthApi(createHttpClient(), baseUrl) }
        var screen by remember { mutableStateOf<Screen>(Screen.Inicio) }

        Surface(Modifier.fillMaxSize()) {
            when (val s = screen) {
                Screen.Inicio -> InicioScreen(
                    onLogin = { screen = Screen.Login },
                    onRegister = { screen = Screen.Register }
                )
                Screen.Login -> LoginScreen(
                    api = api,
                    onSuccess = { email, role -> screen = Screen.Dashboard(email, role) },
                    onBack = { screen = Screen.Inicio }
                )
                Screen.Register -> RegisterScreen(
                    api = api,
                    onSuccess = { screen = Screen.Login },
                    onBack = { screen = Screen.Inicio }
                )
                is Screen.Dashboard -> DashboardScreen(
                    email = s.email,
                    role = s.role,
                    onLogout = { screen = Screen.Inicio }
                )
            }
        }
    }
}