package com.lutefisk.service

import com.lutefisk.model.User
import com.lutefisk.repository.UserRepository
import java.util.*

class UserService(private val userRepository: UserRepository) {
    fun findAll(): List<User> =
        userRepository.findAllUsers()

    fun findById(id: String): User? =
        userRepository.findUserById(UUID.fromString(id))

    fun findByUsername(username: String): User? =
        userRepository.findUserByUsername(username)

    fun save(user: User): User? = when {
        findByUsername(user.username) == null -> {
            userRepository.save(user)
            user
        }
        else -> null

    }

}