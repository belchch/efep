package ru.bfe.efep.app.gv.report

import jakarta.persistence.*
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction

@Entity
@Table(name = "general_view_report_items")
data class GeneralViewReportItem (
    @Id @GeneratedValue
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    var row: GeneralViewReportRow,

    val text: String,

    @OneToMany(mappedBy = "item")
    @OnDelete(action = OnDeleteAction.CASCADE)
    var photos: MutableList<GeneralViewReportPhoto> = mutableListOf()
)