package ru.bfe.efep.app.inspection.photodoc

import jakarta.persistence.*
import ru.bfe.efep.app.inspection.Inspection
import ru.bfe.efep.app.material.Material
import ru.bfe.efep.app.spot.Spot
import ru.bfe.efep.app.structelem.StructElem

@Entity
@Table(name = "photo_docs")
@Inheritance(strategy = InheritanceType.JOINED)
class PhotoDoc(
    @Id @GeneratedValue
    val id: Long? = null,

    @Column(nullable = false)
    var source: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    var inspection: Inspection,

    @ManyToOne(fetch = FetchType.LAZY)
    var spot: Spot? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    var structElem: StructElem? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    var material: Material? = null,

    @Enumerated(EnumType.STRING)
    var type: PhotoDocType? = null
)

enum class PhotoDocType {
    DEFECT, GENERAL_VIEW
}