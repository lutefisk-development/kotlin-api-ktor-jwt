package com.lutefisk.routing.response

import com.lutefisk.util.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class UserResponse(
    @Serializable(UUIDSerializer::class)
    val id: UUID,
    val username: String
)
