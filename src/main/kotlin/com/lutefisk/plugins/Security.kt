package com.lutefisk.plugins

import com.lutefisk.service.JwtService
import com.lutefisk.util.Jwt
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*

fun Application.configureSecurity(jwtService: JwtService) {
    authentication {
        jwt {
            realm = Jwt.realm
            verifier(jwtService.jwtVerifier)

            validate { credential ->
                jwtService.customValidator(credential)
            }
        }

        /**
         * Multiple validations
         *
         * We can set specific endpoints to use this (or any other) validator
         */
        jwt("second-validator") {
            realm = Jwt.realm
            verifier(jwtService.jwtVerifier)

            validate { credential ->
                jwtService.customValidator(credential)
            }
        }
    }
}