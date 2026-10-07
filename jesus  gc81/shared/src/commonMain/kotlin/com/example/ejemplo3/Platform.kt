package com.example.ejemplo3

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform