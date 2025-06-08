package ru.bfe.efep.app.defect.report.row

import jakarta.persistence.*
import ru.bfe.efep.app.defect.report.photo.DefectReportPhoto
import ru.bfe.efep.app.defect.report.structelem.DefectReportStructElem

@Entity
@Table(name = "defect_report_row")
data class DefectReportRow(
    @Id @GeneratedValue
    val id: Long? = null,

    var technicalReport: String? = null,

    var defect: String? = null,

    var standard: String? = null,

    @Column(nullable = false)
    val sortOrder: Int,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    var structElem: DefectReportStructElem,

    @OneToMany(mappedBy = "row", cascade = [CascadeType.ALL])
    var photos: MutableList<DefectReportPhoto> = mutableListOf(),
)