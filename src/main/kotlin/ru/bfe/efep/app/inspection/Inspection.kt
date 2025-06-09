package ru.bfe.efep.app.inspection

import jakarta.persistence.*
import ru.bfe.efep.app.cases.Case
import ru.bfe.efep.app.defect.report.DefectReport
import ru.bfe.efep.app.inspection.photodoc.PhotoDoc
import ru.bfe.efep.app.user.User
import java.time.Instant

@Entity
@Table(name = "inspections")
data class Inspection(
    @Id @GeneratedValue
    val id: Long? = null,

    @Column(length = 500, nullable = false)
    var address: String,

    var performedDate: Instant? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    var performedBy: User? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    var case: Case? = null,

    @OneToMany(mappedBy = "inspection")
    var photoDocs: MutableList<PhotoDoc> = mutableListOf(),

    @OneToMany(mappedBy = "inspection")
    var defectReports: MutableList<DefectReport> = mutableListOf(),
)