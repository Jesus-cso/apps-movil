package com.example

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

object UserRepository {
    private val json = Json { prettyPrint = true; ignoreUnknownKeys = true }
    private val file = File("data/users.json")
    private val lock = Any()

    val registrableRoles = setOf("SUPERVISOR", "CLIENTE", "DOCTOR", "VENDEDOR")

    init {
        if (!file.exists()) {
            file.parentFile.mkdirs()
            val seed = UserRepository::class.java
                .getResource("/users.json")!!
                .readText()
                .removePrefix("\uFEFF")
            file.writeText(seed)
        }
    }

    private fun readAll(): List<User> =
        json.decodeFromString(file.readText().removePrefix("\uFEFF"))

    fun validate(username: String, password: String): User? = synchronized(lock) {
        readAll().firstOrNull { it.username == username && it.password == password }
    }

    fun register(username: String, password: String, role: String): User? = synchronized(lock) {
        val users = readAll()
        if (users.any { it.username.equals(username, ignoreCase = true) }) return null
        val newUser = User(username, password, role)
        file.writeText(json.encodeToString(users + newUser))
        newUser
    }
}