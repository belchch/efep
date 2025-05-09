package ru.bfe.efep.app.region

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "regions")
data class Region(
    @Id @GeneratedValue
    val id: Long? = null,

    @Column(length = 50)
    var code: String,

    var name: String
)