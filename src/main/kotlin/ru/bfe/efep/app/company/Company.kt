package ru.bfe.efep.app.company

import jakarta.persistence.*

@Entity
@Table(name = "companies")
data class Company(
    @Id @GeneratedValue
    val id: Long? = null,

    @Column(nullable = false, length = 500)
    var name: String,

    @Column(length = 50)
    var inn: String?
)