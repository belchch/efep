package ru.bfe.efep.app.user

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table

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