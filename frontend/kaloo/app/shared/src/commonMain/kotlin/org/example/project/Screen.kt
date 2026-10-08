package org.example.project

sealed interface Screen {
    data object Inicio : Screen
    data object Login : Screen
    data object Register : Screen
    data class Dashboard(val email: String, val role: String?) : Screen
}