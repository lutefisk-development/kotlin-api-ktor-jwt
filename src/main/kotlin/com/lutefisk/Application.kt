package com.lutefisk

import com.lutefisk.plugins.configureSerialization
import com.lutefisk.repository.UserRepository
import com.lutefisk.routing.configureRouting
import com.lutefisk.service.UserService
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "127.0.0.1", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureSerialization()
    configureRouting(
        UserService(UserRepository())
    )
}

/**
 * TODO
 * Encrypt password before persisting
 */
