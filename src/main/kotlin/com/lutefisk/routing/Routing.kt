package com.lutefisk.routing

import com.lutefisk.service.UserService
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting(userService: UserService) {
    routing {
        route("/api/user") {
            userRoute(userService)
        }
    }
}