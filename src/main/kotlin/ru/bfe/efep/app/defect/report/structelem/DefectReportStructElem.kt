package ru.bfe.efep.app.defect.report.structelem

import jakarta.persistence.*
import ru.bfe.efep.app.defect.report.spot.DefectReportSpot
import ru.bfe.efep.app.defect.report.row.DefectReportRow

@Entity
@Table(name = "defect_report_struct_elems")
data class DefectReportStructElem(
    @Id @GeneratedValue
    val id: Long? = null,

    var text: String,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    var spot: DefectReportSpot,

    @OneToMany(mappedBy = "structElem", cascade = [(CascadeType.ALL)])
    var rows: MutableList<DefectReportRow> = mutableListOf(),
)