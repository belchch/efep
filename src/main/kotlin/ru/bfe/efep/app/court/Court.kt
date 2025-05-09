package ru.bfe.efep.app.court

import jakarta.persistence.*

@Entity
@Table(name = "courts")
data class Court(
    @Id
    @GeneratedValue()
    val id: Long? = null,

    @Column(nullable = false, length = 500)
    var name: String,

    @Column(length = 20)
    var postalCode: String?,

    @Column(length = 100)
    var region: String?
)