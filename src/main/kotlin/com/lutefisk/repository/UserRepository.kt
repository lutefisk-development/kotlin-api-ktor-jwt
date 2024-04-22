package com.lutefisk.repository

import com.lutefisk.model.User
import java.util.UUID

class UserRepository {
    private val users = mutableListOf<User>()

    fun findAllUsers(): List<User> =
        users

    fun findUserById(id: UUID): User? =
        users.firstOrNull { user -> user.id == id }

    fun findUserByUsername(username: String): User? =
        users.firstOrNull { user -> user.username == username }

    fun save(user: User): Boolean =
        users.add(user)
}