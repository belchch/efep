package ru.bfe.efep.app.defect.report

import ru.bfe.efep.app.defect.report.spot.DefectReportSpotResponse
import ru.bfe.efep.app.defect.report.spot.toResponse

data class DefectReportResponse(
    val id: Long,
    val spots: MutableList<DefectReportSpotResponse>,
    val useTechnicalReport: Boolean
)

fun DefectReport.toResponse() = DefectReportResponse(
    id = id!!,
    spots = spots.map { it.toResponse() }.toMutableList(),
    useTechnicalReport = useTechnicalReport
)