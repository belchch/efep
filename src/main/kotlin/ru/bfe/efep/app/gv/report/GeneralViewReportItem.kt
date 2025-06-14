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

@Entity
@Table(name = "general_view_report_items")
class GeneralViewReportItem (
    @Id @GeneratedValue
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(nullable = false)
    var row: GeneralViewReportRow,

    val text: String,

    @OneToMany(mappedBy = "item")
    var photos: MutableList<GeneralViewReportPhoto> = mutableListOf()
)