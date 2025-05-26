package ru.bfe.efep.app.defect.flaw

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "flaws")
class Flaw (
    @Id @GeneratedValue
    val id: Long? = null,

    @Column(length = 50, nullable = false)
    var name: String
)