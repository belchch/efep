package ru.bfe.efep.app.auth

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.bfe.efep.app.config.security.JwtService

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authService: AuthService,
) {
    @PostMapping("/login")
    fun login(@RequestBody request: AuthRequest): ResponseEntity<AuthResponse> {
        val jwtToken = authService.login(request.username, request.password)
        return ResponseEntity.ok(AuthResponse(jwtToken))
    }

    @PostMapping("/register")
    fun register(
        @RequestBody request: RegisterRequest
    ): ResponseEntity<AuthResponse> {
        val jwtToken = authService.register(request.username, request.password)

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(AuthResponse(jwtToken))
    }
}

data class AuthRequest(val username: String, val password: String)
data class AuthResponse(val token: String)
data class RegisterRequest(val username: String, val password: String)