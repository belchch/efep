package ru.bfe.efep.app.standard

import jakarta.persistence.*

@Entity
@Table(name = "standards")
data class Standard(
    @Id @GeneratedValue
    val id: Long? = null,

    @Column(length = 250, nullable = false)
    val name: String,

    @Column(length = 500)
    val description: String,
)