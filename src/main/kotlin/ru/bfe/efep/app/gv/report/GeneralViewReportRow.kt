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
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction

@Entity
@Table(name = "general_view_report_rows")
data class GeneralViewReportRow (
    @Id @GeneratedValue
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    var report: GeneralViewReport,

    @OneToMany(mappedBy = "row")
    @OnDelete(action = OnDeleteAction.CASCADE)
    var items: MutableList<GeneralViewReportItem> = mutableListOf()
)