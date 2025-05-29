package ru.bfe.efep.app.inspection.photodoc

import jakarta.persistence.*
import ru.bfe.efep.app.defect.Defect
import ru.bfe.efep.app.defect.flaw.Flaw
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

    @Column(nullable = false, columnDefinition = "text[] DEFAULT '{}'")
    var sources: List<String> = emptyList(),

    @Column(length = 500, nullable = false, columnDefinition = "text[] DEFAULT '{}'")
    var urls: List<String> = emptyList(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    var inspection: Inspection,

    @ManyToOne(fetch = FetchType.LAZY)
    var spot: Spot? = null,

    @Enumerated(EnumType.STRING)
    var type: PhotoDocType? = null,

    @Embedded
    var defectInfo: DefectInfo? = null
) {
    @PrePersist
    @PreUpdate
    private fun validate() {
        if (type == PhotoDocType.GENERAL_VIEW) {
            require(defectInfo == null) { "Defect info must be null for GENERAL_VIEW type" }
        }
    }
}

enum class PhotoDocType {
    DEFECT, GENERAL_VIEW
}

@Embeddable
data class DefectInfo (
    @ManyToOne(fetch = FetchType.LAZY)
    var structElem: StructElem? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    var material: Material? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    var flaw: Flaw? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    var defect: Defect? = null
)