package ru.bfe.efep.app.auth

import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import ru.bfe.efep.app.config.security.JwtService
import ru.bfe.efep.app.user.Role
import ru.bfe.efep.app.user.User
import ru.bfe.efep.app.user.UserRepository

@Service
class AuthService(
    private val authenticationManager: AuthenticationManager,
    private val jwtService: JwtService,
    private val passwordEncoder: PasswordEncoder,
    private val userRepository: UserRepository,
    private val refreshTokenRepository: RefreshTokenRepository
) {
    fun login(username: String, password: String): Pair<String, String> {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                username,
                password
            )
        )

        val (accessToken, refreshToken) = generateTokens(username)
        return (accessToken to refreshToken)
    }

    fun refreshToken(token: String): Pair<String, String> {
        if (!jwtService.validateToken(token)) {
            throw RuntimeException("Invalid refresh token")
        }

        val refreshToken = refreshTokenRepository.findByToken(token)
            ?: throw RuntimeException("Refresh token not found")

        if (jwtService.isTokenExpired(token)) {
            refreshTokenRepository.delete(refreshToken)
            throw RuntimeException("Refresh token expired")
        }

        val (newAccessToken, newRefreshToken) = generateTokens(refreshToken.username)
        refreshTokenRepository.delete(refreshToken)
        return (newAccessToken to newRefreshToken)
    }

    fun register(username: String, password: String): Pair<String, String> {
        if (userRepository.existsByUsername(username)) {
            throw IllegalArgumentException("User with name $username already exists")
        }

        val user = User(
            username = username,
            password = passwordEncoder.encode(password),
            role = Role.USER
        )

        userRepository.save(user)
        return generateTokens(username)
    }

    private fun generateTokens(username: String): Pair<String, String> {
        val accessToken = jwtService.generateAccessToken(username)
        val refreshToken = jwtService.generateRefreshToken(username)

        refreshTokenRepository.save(
            RefreshToken(
                token = refreshToken,
                username = username,
                expirationDate = jwtService.extractExpiration(refreshToken).toInstant()
            )
        )

        return (accessToken to refreshToken)
    }

    fun logout(refreshToken: String) {
        refreshTokenRepository.findByToken(refreshToken)?.let {
            refreshTokenRepository.delete(it)
        }
    }
}