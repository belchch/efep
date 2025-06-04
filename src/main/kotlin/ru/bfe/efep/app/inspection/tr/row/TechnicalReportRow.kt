package ru.bfe.efep.app.inspection.tr.row

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import ru.bfe.efep.app.inspection.photodoc.PhotoDoc
import ru.bfe.efep.app.inspection.tr.TechnicalReport
import ru.bfe.efep.app.standard.Standard

@Entity
@Table(name = "tecnical_report_rows")
data class TechnicalReportRow(
    @Id @GeneratedValue
    var id: Long? = null,

    @Column(nullable = false, length = 500)
    var description: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    var standard: Standard,

    @OneToOne(mappedBy = "defectInfo.technicalReportRow")
    var photoDoc: PhotoDoc? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    var technicalReport: TechnicalReport,
)