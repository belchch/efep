package ru.bfe.efep.app.auth

import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
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
    private val userRepository: UserRepository
) {
    fun login(username: String, password: String): String {
        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                username,
                password
            )
        )

        val userDetails = authentication.principal as UserDetails
        return jwtService.generateToken(userDetails)
    }

    fun register(username: String, password: String): String {
        if (userRepository.existsByUsername(username)) {
            throw IllegalArgumentException("User with name $username already exists")
        }

        val user = User(
            username = username,
            password = passwordEncoder.encode(password),
            role = Role.USER
        )

        val savedUser = userRepository.save(user)
        return jwtService.generateToken(savedUser)
    }
}