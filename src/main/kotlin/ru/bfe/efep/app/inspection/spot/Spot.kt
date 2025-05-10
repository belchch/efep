package ru.bfe.efep.app.inspection.spot

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Inheritance
import jakarta.persistence.InheritanceType
import jakarta.persistence.Table

@Entity
@Table(name = "spots")
@Inheritance(strategy = InheritanceType.JOINED)
class Spot(
    @Id @GeneratedValue
    val id: Long? = null,

    @Column(nullable = false)
    var name: String
)