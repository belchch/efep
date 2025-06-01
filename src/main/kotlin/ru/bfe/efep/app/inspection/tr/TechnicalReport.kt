package ru.bfe.efep.app.inspection.tr

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.ForeignKey
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import ru.bfe.efep.app.inspection.Inspection

@Entity
@Table(name = "technical_reports")
data class TechnicalReport(
    @Id @GeneratedValue
    var id: Long? = null,
    var name: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    var inspection: Inspection,

    @OneToMany(fetch = FetchType.LAZY)
    var technicalReportRows: MutableList<TechnicalReportRow> = mutableListOf(),
)