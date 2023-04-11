package com.mennad.api.association.helpers

import java.security.SecureRandom
import java.security.spec.KeySpec
import javax.crypto.SecretKey
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

fun ByteArray.toHexString() = joinToString("") { "%02x".format(it) }

object PasswordGenerator {

    const val ALGORITHM = "PBKDF2WithHmacSHA512"
    const val ITERATIONS = 120_000
    const val KEY_LENGTH = 256
    const val SECRET = "SomeRandomSecret"

    /**
     * Returns a random salt.
     * @return
     */
    private fun generateRandomSalt(): ByteArray {
        val random = SecureRandom()
        val salt = ByteArray(16)
        random.nextBytes(salt)
        return salt
    }

    /**
     * Returns the PBKDF2WithHmacSHA512 hash of the given password.
     * @param password
     * @return
     */
    fun generateHash(password: String): String {
        val salt = generateRandomSalt();
        val combinedSalt = "$salt$SECRET".toByteArray()
        val factory: SecretKeyFactory = SecretKeyFactory.getInstance(ALGORITHM)
        val spec: KeySpec = PBEKeySpec(password.toCharArray(), combinedSalt, ITERATIONS, KEY_LENGTH)
        val key: SecretKey = factory.generateSecret(spec)
        val hash: ByteArray = key.encoded
        return hash.toHexString()
    }

}