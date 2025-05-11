package ru.bfe.efep.app.judge

import jakarta.persistence.*
import ru.bfe.efep.app.court.Court

@Entity
@Table(name = "judges")
data class Judge (
    @Id @GeneratedValue
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    var court: Court,

    @Column(nullable = false, length = 50)
    var firstName: String,

    @Column(length = 50)
    var middleName: String?,

    @Column(nullable = false, length = 50)
    var lastName: String,
)