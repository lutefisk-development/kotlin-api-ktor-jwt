package com.lutefisk.routing

import com.lutefisk.service.JwtService
import com.lutefisk.service.UserService
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting(userService: UserService, jwtService: JwtService) {
    routing {
        route("api/auth") {
            authRoute(jwtService)
        }

        route("/api/user") {
            userRoute(userService)
        }
    }
}