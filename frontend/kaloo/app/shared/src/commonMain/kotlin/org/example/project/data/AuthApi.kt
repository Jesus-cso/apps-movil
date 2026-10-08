package org.example.project.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess

class AuthApi(
    private val client: HttpClient,
    private val baseUrl: String
) {
    suspend fun login(req: LoginRequest): Result<AuthResponse> = runCatching {
        val res = client.post("$baseUrl/login") {
            contentType(ContentType.Application.Json)
            setBody(req)
        }
        handle(res)
    }

    suspend fun register(req: RegisterRequest): Result<AuthResponse> = runCatching {
        val res = client.post("$baseUrl/register") {
            contentType(ContentType.Application.Json)
            setBody(req)
        }
        handle(res)
    }

    private suspend fun handle(res: HttpResponse): AuthResponse {
        if (res.status.isSuccess()) return res.body<AuthResponse>()
        val raw = runCatching { res.bodyAsText() }.getOrNull()
        val msg = runCatching { res.body<AuthResponse>().message }.getOrNull()
        error(msg ?: "Error ${res.status.value}: $raw")
    }
}