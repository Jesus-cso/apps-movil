package org.example.project

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import org.example.project.data.AuthApi
import org.example.project.data.baseUrl
import org.example.project.data.createHttpClient

private fun screenForRole(username: String, role: String?): Screen =
    when (Role.from(role)) {
        Role.ADMINISTRADOR -> Screen.Admin(username)
        Role.INSTRUCTOR -> Screen.Instructor(username)
        Role.CLIENTE -> Screen.Cliente(username)
        null -> Screen.Cliente(username) // rol desconocido: vista de menor privilegio
    }

@Composable
fun App() {
    MaterialTheme {
        val api = remember { AuthApi(createHttpClient(), baseUrl) }
        var screen by remember { mutableStateOf<Screen>(Screen.Inicio) }
        val logout = { screen = Screen.Inicio }

        Surface(Modifier.fillMaxSize()) {
            when (val s = screen) {
                Screen.Inicio -> InicioScreen(
                    onLogin = { screen = Screen.Login },
                    onRegister = { screen = Screen.Register }
                )
                Screen.Login -> LoginScreen(
                    api = api,
                    onSuccess = { username, role -> screen = screenForRole(username, role) },
                    onBack = { screen = Screen.Inicio }
                )
                Screen.Register -> RegisterScreen(
                    api = api,
                    onSuccess = { screen = Screen.Login },
                    onBack = { screen = Screen.Inicio }
                )
                is Screen.Admin -> AdminScreen(s.username, logout)
                is Screen.Instructor -> InstructorScreen(s.username, logout)
                is Screen.Cliente -> ClienteScreen(s.username, logout)
            }
        }
    }
}