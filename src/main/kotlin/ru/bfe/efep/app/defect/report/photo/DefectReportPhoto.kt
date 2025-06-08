package ru.bfe.efep.app.defect.report.photo

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import ru.bfe.efep.app.defect.report.row.DefectReportRow

@Entity
@Table(name = "defect_report_photo")
data class DefectReportPhoto(
    @Id @GeneratedValue
    val id: Long? = null,

    var source: String,
    var url: String? = null,
    var used: Boolean,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    var row: DefectReportRow
)