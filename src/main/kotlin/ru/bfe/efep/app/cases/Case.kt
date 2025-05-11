package ru.bfe.efep.app.cases

import jakarta.persistence.*
import ru.bfe.efep.app.company.Company
import ru.bfe.efep.app.court.Court
import ru.bfe.efep.app.judge.Judge
import ru.bfe.efep.app.region.Region
import ru.bfe.efep.app.user.User
import java.time.Instant

@Entity
@Table(name = "cases")
data class Case(
    @Id
    @GeneratedValue
    val id: Long? = null,

    @Column(length = 50, nullable = false)
    var number: String,

    @Enumerated(EnumType.STRING)
    @Basic(optional=false)
    @Column(length = 50, nullable = false)
    var status: CaseStatus,

    @Enumerated(EnumType.STRING)
    @Basic(optional=false)
    @Column(length = 50, nullable = false)
    var priority: CasePriority,

    @Column(length = 500, nullable = false)
    var facilityAddress: String,

    @ManyToOne(fetch = FetchType.LAZY)
    var court: Court?,

    @ManyToOne(fetch = FetchType.LAZY)
    var judge: Judge?,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    var company: Company,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    var region: Region,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    var createdBy: User,

    @Column(nullable = false)
    var createdAt: Instant
)

enum class CaseStatus {
    OPEN, IN_PROGRESS, DONE;
}

enum class CasePriority {
    HIGH, MEDIUM, LOW;
}