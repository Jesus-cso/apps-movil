package org.example.project

enum class Role {
    ADMINISTRADOR, INSTRUCTOR, CLIENTE;

    companion object {
        fun from(value: String?): Role? =
            entries.firstOrNull { it.name.equals(value, ignoreCase = true) }
    }
}