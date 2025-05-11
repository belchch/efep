package ru.bfe.efep.app.region

import jakarta.persistence.*

@Entity
@Table(name = "regions")
data class Region(
    @Id @GeneratedValue
    val id: Long? = null,

    @Column(length = 50)
    var code: String,

    var name: String
)