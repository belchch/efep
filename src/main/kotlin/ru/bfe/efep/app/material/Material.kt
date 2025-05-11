package ru.bfe.efep.app.material

import jakarta.persistence.*

@Entity
@Table(name = "materials")
data class Material(
    @Id @GeneratedValue
    val id: Long? = null,

    @Column(nullable = false)
    var name: String
)