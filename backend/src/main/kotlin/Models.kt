package com.example

import kotlinx.serialization.Serializable

@Serializable
data class User(val username: String, val password: String, val role: String)

@Serializable
data class LoginRequest(val username: String, val password: String)

@Serializable
data class RegisterRequest(
    val username: String,
    val password: String,
    val role: String = "CLIENTE"
)

@Serializable
data class LoginResponse(
    val success: Boolean,
    val username: String? = null,
    val role: String? = null,
    val message: String? = null
)
