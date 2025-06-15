package ru.bfe.efep.app.gv.report

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "general_view_report_photos")
data class GeneralViewReportPhoto(
    @Id @GeneratedValue
    var id: Long? = null,

    @Column(nullable = false)
    val source: String,

    @Column(length = 1000)
    var url: String? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    val item: GeneralViewReportItem
)