package org.example.project

sealed interface Screen {
    data object Inicio : Screen
    data object Login : Screen
    data object Register : Screen
    data class Admin(val username: String) : Screen
    data class Instructor(val username: String) : Screen
    data class Cliente(val username: String) : Screen
}