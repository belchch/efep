package ru.bfe.efep.app.spot

import jakarta.persistence.*

@Entity
@Table(name = "spots")
@Inheritance(strategy = InheritanceType.JOINED)
data class Spot(
    @Id @GeneratedValue
    val id: Long? = null,

    @Column(nullable = false)
    var name: String
)