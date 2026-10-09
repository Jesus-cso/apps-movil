# Kaloo

Aplicación móvil multiplataforma con backend local en Ktor y frontend en Kotlin Multiplatform (Compose Multiplatform).

Repositorio: https://github.com/Jesus-cso/apps-movil

## Integrantes

- (agrega aquí los nombres del equipo)

## Estructura del proyecto

    backend/                  Servidor Ktor (generado con el wizard de Ktor)
      data/users.json         Usuarios guardados (se actualiza al registrarse)
      src/main/kotlin/        Routing, Models, UserRepository, Serialization, Cors
    frontend/kaloo/           App Kotlin Multiplatform
      app/shared/             Pantallas, navegación y cliente HTTP (commonMain)
      app/androidApp/         Entrada Android
      app/iosApp/             Entrada iOS
      app/webApp/             Entrada Web

## Roles y navegación

La app tiene 3 roles: ADMINISTRADOR, INSTRUCTOR y CLIENTE. Al iniciar sesión, el backend devuelve el rol del usuario y la app abre la pantalla de ese rol (función screenForRole en App.kt).

En el registro se puede elegir CLIENTE o INSTRUCTOR. El rol ADMINISTRADOR no se puede crear desde la app: se agrega a mano en users.json.

## Usuarios de prueba

| Usuario    | Contraseña | Rol           |
|------------|------------|---------------|
| admin      | admin123   | ADMINISTRADOR |
| instructor | ins123     | INSTRUCTOR    |
| cliente    | cli123     | CLIENTE       |

## Cómo ejecutar

Backend (responde en http://localhost:8080):

    cd backend
    gradlew.bat run

App: abrir la carpeta frontend/kaloo en Android Studio y ejecutar androidApp. El emulador de Android se conecta al backend con la dirección 10.0.2.2.

Versión web:

    cd frontend/kaloo
    gradlew.bat :app:webApp:wasmJsBrowserDevelopmentRun

## Flujo de datos y serialización

Pantalla (Compose) -> AuthApi (Ktor Client + kotlinx.serialization) -> POST /login o /register -> Ktor Server (ContentNegotiation) -> UserRepository -> data/users.json

Los modelos se definen en cada proyecto con @Serializable: LoginRequest y RegisterRequest en ambos lados, y LoginResponse en el backend equivale a AuthResponse en el frontend.

## Capturas

- (agrega aquí las capturas de pantalla)