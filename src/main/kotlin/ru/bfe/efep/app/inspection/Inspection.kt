package ru.bfe.efep.app.inspection

import jakarta.persistence.*
import ru.bfe.efep.app.user.User
import java.time.LocalDateTime

@Entity
@Table(name = "inspections")
data class Inspection(
    @Id @GeneratedValue
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    var createdBy: User,

    @Column(length = 500)
    var address: String,

    @Column(nullable = false)
    var performedDate: LocalDateTime? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    var performedBy: User? = null
)