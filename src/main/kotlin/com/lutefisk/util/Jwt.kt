package com.lutefisk.util

object Jwt {
    val audience = "my-audience"
    val issuer = "http://localhost"
    val realm = "my realm"
    val secret = ${SECRET}
}