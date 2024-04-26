package com.lutefisk.service

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.lutefisk.routing.request.LoginRequest
import com.lutefisk.util.Jwt
import com.lutefisk.util.generateHash
import io.ktor.server.auth.jwt.*
import java.util.*

class JwtService(private val userService: UserService) {
    val jwtVerifier: JWTVerifier =
        JWT.require(Algorithm.HMAC256(Jwt.secret))
            .withAudience(Jwt.audience)
            .withIssuer(Jwt.issuer)
            .build()

    fun createJwtToken(loginRequest: LoginRequest): String? {
        val foundUser = userService.findByUsername(loginRequest.username)
        val hashedLoginRequestPassword = generateHash(loginRequest.password)

        return when {
            foundUser != null && hashedLoginRequestPassword == foundUser.password ->
                JWT.create()
                    .withAudience(Jwt.audience)
                    .withIssuer(Jwt.issuer)
                    .withClaim("username", foundUser.username)
                    .withExpiresAt(Date(System.currentTimeMillis() + 3_600_000))
                    .sign(Algorithm.HMAC256(Jwt.secret))
            else -> null
        }
    }

    fun customValidator(credential: JWTCredential): JWTPrincipal? {
        val username = extractUsername(credential)
        val foundUser = username?.let { userService.findByUsername(it) }

        return foundUser?.let {
            if (audienceMatches(credential)) JWTPrincipal(credential.payload) else null
        }
    }

    private fun audienceMatches(credential: JWTCredential): Boolean =
        credential.payload.audience.contains(Jwt.audience)

    private fun extractUsername(credential: JWTCredential): String? =
        credential.payload.getClaim("username").asString()
}