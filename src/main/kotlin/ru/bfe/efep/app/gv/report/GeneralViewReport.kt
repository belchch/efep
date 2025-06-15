package ru.bfe.efep.app.gv.report

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import ru.bfe.efep.app.inspection.Inspection

@Entity
@Table(name = "general_view_reports")
data class GeneralViewReport(
    @Id @GeneratedValue
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    val inspection: Inspection,

    @OneToMany(mappedBy = "report")
    val rows: MutableList<GeneralViewReportRow> = mutableListOf()
)