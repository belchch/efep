package ru.bfe.efep.app.defect.report.spot

import ru.bfe.efep.app.defect.report.structelem.DefectReportStructElemResponse
import ru.bfe.efep.app.defect.report.structelem.toResponse

data class DefectReportSpotResponse(
    val id: Long,
    val text: String,
    val structElems: List<DefectReportStructElemResponse>
)

fun DefectReportSpot.toResponse() = DefectReportSpotResponse(
    id = id!!,
    text = text,
    structElems = structElems.map { it.toResponse() }
)