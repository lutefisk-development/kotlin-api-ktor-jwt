package com.lutefisk.util

import java.security.spec.KeySpec
import javax.crypto.SecretKey
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

private const val ALGORITHM = "PBKDF2WithHmacSHA512"
private const val ITERATIONS = 120_000
private const val KEY_LENGTH = 256
private const val SALT = "95fe16b4c9c755c43365c821c9c07f65"     // This should ofc not be stored here!!
private const val SECRET = "RandomSecret12345"                  // This should ofc not be stored here!!

@OptIn(ExperimentalStdlibApi::class)
fun generateHash(password: String): String {
    val combined = "$SALT$SECRET".toByteArray()

    val factory: SecretKeyFactory = SecretKeyFactory.getInstance(ALGORITHM)
    val spec: KeySpec = PBEKeySpec(password.toCharArray(), combined, ITERATIONS, KEY_LENGTH)
    val key: SecretKey = factory.generateSecret(spec)

    return key.encoded.toHexString()
}