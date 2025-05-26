package ru.bfe.efep.app.defect

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import ru.bfe.efep.app.defect.flaw.Flaw
import ru.bfe.efep.app.material.Material
import ru.bfe.efep.app.structelem.StructElem

@Entity
@Table(name = "defects")
data class Defect(
    @Id @GeneratedValue
    val id: Long? = null,

    @Column(length = 500, nullable = false)
    var template: String,

    @Column(length = 500, nullable = false)
    var standard: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    var structElem: StructElem,

    @ManyToOne(fetch = FetchType.LAZY)
    var material: Material? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    var flaw: Flaw? = null,
)