package ru.bfe.efep.app.defect.report

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import ru.bfe.efep.app.defect.report.spot.DefectReportSpot
import ru.bfe.efep.app.inspection.Inspection

@Entity
@Table(name = "defect_reports")
data class DefectReport(
    @Id @GeneratedValue
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    var inspection: Inspection,

    @OneToMany(mappedBy = "report", cascade = [(CascadeType.ALL)])
    var spots: MutableList<DefectReportSpot> = mutableListOf(),
)