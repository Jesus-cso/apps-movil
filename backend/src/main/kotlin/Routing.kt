package com.example

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/") { call.respondText("Kaloo backend funcionando") }

        post("/login") {
            val req = call.receive<LoginRequest>()
            val user = UserRepository.validate(req.username, req.password)
            if (user != null) {
                call.respond(LoginResponse(true, user.username, user.role))
            } else {
                call.respond(
                    HttpStatusCode.Unauthorized,
                    LoginResponse(false, message = "Credenciales inválidas")
                )
            }
        }

        post("/register") {
            val req = call.receive<RegisterRequest>()

            if (req.username.isBlank() || req.password.length < 4) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    LoginResponse(false, message = "Usuario vacío o contraseña menor a 4 caracteres")
                )
                return@post
            }

            val role = req.role.uppercase()
            if (role !in UserRepository.registrableRoles) {
                call.respond(HttpStatusCode.BadRequest, LoginResponse(false, message = "Rol no permitido"))
                return@post
            }

            val user = UserRepository.register(req.username.trim(), req.password, role)
            if (user == null) {
                call.respond(HttpStatusCode.Conflict, LoginResponse(false, message = "El usuario ya existe"))
            } else {
                call.respond(HttpStatusCode.Created, LoginResponse(true, user.username, user.role))
            }
        }
    }
}
