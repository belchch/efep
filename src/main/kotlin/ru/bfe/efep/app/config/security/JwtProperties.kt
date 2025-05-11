package ru.bfe.efep.app.config.security

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "jwt")
data class JwtProperties(
    val secret: String,
    val expiration: Long
) {
    init {
        require(secret.length >= 32) { "JWT secret must be at least 32 characters" }
        require(expiration > 0) { "Expiration must be positive" }
    }
}