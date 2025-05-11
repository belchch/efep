package ru.bfe.efep.app.user

import jakarta.persistence.*

@Entity
@Table(name = "users")
data class User(
    @Id @GeneratedValue
    val id: Long? = null,

    @Column(length = 50, nullable = false)
    var username: String,

    @Column(nullable = false)
    var password: String
)