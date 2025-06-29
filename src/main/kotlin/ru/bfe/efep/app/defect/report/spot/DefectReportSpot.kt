package ru.bfe.efep.app.defect.report.spot

import jakarta.persistence.*
import ru.bfe.efep.app.defect.report.DefectReport
import ru.bfe.efep.app.defect.report.structelem.DefectReportStructElem

@Entity
@Table(name = "defect_report_spots")
data class DefectReportSpot(
    @Id @GeneratedValue
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    val report: DefectReport,

    @Column(nullable = false)
    val text: String,

    @Column(nullable = false)
    var sortOrder: Int,

    @OneToMany(mappedBy = "spot", cascade = [CascadeType.ALL])
    var structElems: MutableList<DefectReportStructElem> = mutableListOf(),
)