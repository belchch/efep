package ru.bfe.efep.app.company

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "companies")
data class Company(
    @Id @GeneratedValue
    val id: Long? = null,

    @Column(nullable = false, length = 500)
    var name: String,

    @Column(length = 50)
    var inn: String
)