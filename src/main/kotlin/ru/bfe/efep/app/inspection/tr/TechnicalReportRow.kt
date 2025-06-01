package ru.bfe.efep.app.inspection.tr

import jakarta.persistence.*
import ru.bfe.efep.app.inspection.photodoc.PhotoDoc
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
)