package ru.bfe.efep.app.auth

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import java.time.Instant

@Entity
data class RefreshToken(
    @Id @GeneratedValue
    val id: Long? = null,

    @Column(nullable = false, unique = true)
    val token: String,

    @Column(nullable = false)
    val username: String,

    @Column(nullable = false)
    val expirationDate: Instant
)