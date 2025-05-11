package ru.bfe.efep.app.structelem

import jakarta.persistence.*
import ru.bfe.efep.app.material.Material

@Entity
@Table(name = "struct_elems")
data class StructElem(
    @Id @GeneratedValue
    val id: Long? = null,

    @Column(nullable = false)
    var name: String,

    @ManyToMany(cascade = [(CascadeType.MERGE)])
    val materials: List<Material>
)