package org.example.project.data

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(val username: String, val password: String)

@Serializable
data class RegisterRequest(val username: String, val password: String, val role: String)

@Serializable
data class AuthResponse(
    val success: Boolean = false,
    val username: String? = null,
    val role: String? = null,
    val message: String? = null
)