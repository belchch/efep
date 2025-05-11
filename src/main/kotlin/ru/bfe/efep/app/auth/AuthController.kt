package ru.bfe.efep.app.auth

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authService: AuthService,
) {
    @PostMapping("/login")
    fun login(@RequestBody request: CredentialsRequest): ResponseEntity<TokenResponse> {
        val (accessToken, refreshToken) = authService.login(request.username, request.password)

        return ResponseEntity.ok(TokenResponse(
            accessToken = accessToken,
            refreshToken = refreshToken
        ))
    }

    @PostMapping("/refresh")
    fun refresh(@RequestBody request: RefreshTokenRequest): ResponseEntity<TokenResponse> {
        val (accessToken, refreshToken) = authService.refreshToken(request.refreshToken)

        return ResponseEntity.ok(TokenResponse(
            accessToken = accessToken,
            refreshToken = refreshToken
        ))
    }

    @PostMapping("/logout")
    fun logout(@RequestBody request: RefreshTokenRequest): ResponseEntity<Any> {
        authService.logout(request.refreshToken)
        return ResponseEntity.ok().build()
    }

    @PostMapping("/register")
    fun register(
        @RequestBody request: CredentialsRequest
    ): ResponseEntity<TokenResponse> {
        val (accessToken, refreshToken) = authService.register(request.username, request.password)

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(TokenResponse(
                accessToken = accessToken,
                refreshToken = refreshToken
            ))
    }
}

data class CredentialsRequest(val username: String, val password: String)
data class TokenResponse(val accessToken: String, val refreshToken: String)
data class RefreshTokenRequest(val refreshToken: String)