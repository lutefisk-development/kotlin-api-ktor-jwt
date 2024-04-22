package com.lutefisk.routing

import com.lutefisk.model.User
import com.lutefisk.routing.request.UserRequest
import com.lutefisk.routing.response.UserResponse
import com.lutefisk.service.UserService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Route.userRoute(userService: UserService) {
    get {
        call.respond(userService.findAll().map(User::toResponse))
    }

    get("/{id}") {
        val id: String = call.parameters["id"]
            ?: return@get call.respond(HttpStatusCode.BadRequest)

        val foundUser = userService.findById(id)
            ?: return@get call.respond(HttpStatusCode.NotFound)

        call.respond(foundUser.toResponse())
    }

    post {
        val userRequest = call.receive<UserRequest>()

        val createdUser = userService.save(userRequest.toModel())
            ?: return@post call.respond(HttpStatusCode.BadRequest)

        call.response.header(name = "id", value = createdUser.id.toString())
        call.respond(HttpStatusCode.Created)
    }
}

private fun UserRequest.toModel(): User =
    User(
        UUID.randomUUID(),
        this.username,
        this.password
    )

private fun User.toResponse(): UserResponse =
    UserResponse(
        this.id,
        this.username
    )