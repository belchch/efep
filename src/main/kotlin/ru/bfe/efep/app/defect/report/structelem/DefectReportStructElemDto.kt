package ru.bfe.efep.app.defect.report.structelem

import ru.bfe.efep.app.defect.report.row.DefectReportRowResponse
import ru.bfe.efep.app.defect.report.row.toResponse

data class DefectReportStructElemResponse(
    val id: Long,
    val text: String,
    val rows: List<DefectReportRowResponse>
)

fun DefectReportStructElem.toResponse() = DefectReportStructElemResponse(
    id = id!!,
    text = text,
    rows = rows.map { it.toResponse() }
)