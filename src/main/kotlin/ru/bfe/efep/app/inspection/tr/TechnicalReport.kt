package ru.bfe.efep.app.inspection.tr

import jakarta.persistence.*
import ru.bfe.efep.app.inspection.Inspection
import ru.bfe.efep.app.inspection.tr.row.TechnicalReportRow

@Entity
@Table(name = "technical_reports")
data class TechnicalReport(
    @Id @GeneratedValue
    var id: Long? = null,
    var name: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    var inspection: Inspection,

    @OneToMany(mappedBy = "technicalReport", cascade = [CascadeType.ALL])
    var technicalReportRows: MutableList<TechnicalReportRow> = mutableListOf(),
)