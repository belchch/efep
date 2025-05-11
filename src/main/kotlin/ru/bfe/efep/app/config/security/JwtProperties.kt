package ru.bfe.efep.app.config.security

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "jwt")
data class JwtProperties(
    val secret: String,
    val accessTokenExpiration: Long,
    val refreshTokenExpiration: Long
) {
    init {
        require(secret.length >= 32) { "JWT secret must be at least 32 characters" }
        require(accessTokenExpiration > 0) { "Access token expiration must be positive" }
        require(refreshTokenExpiration > 0) { "Refresh token expiration must be positive" }
    }
}