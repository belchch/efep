package ru.bfe.efep.app.inspection.tr

import ru.bfe.efep.app.inspection.Inspection
import ru.bfe.efep.app.inspection.tr.row.TechnicalReportRowResponse
import ru.bfe.efep.app.inspection.tr.row.toResponse

data class TechnicalReportResponse(
    val id: Long,
    val name: String,
    val rows: List<TechnicalReportRowResponse>,
)

data class TechnicalReportCreateRequest(
    val name: String,
)

data class TechnicalReportUpdateRequest(
    val name: String,
)

fun TechnicalReportCreateRequest.toEntity(inspection: Inspection) = TechnicalReport(
    name = name,
    inspection = inspection,
)

fun TechnicalReportUpdateRequest.toEntity(
    original: TechnicalReport
) = TechnicalReport(
    id = original.id,
    name = name,
    inspection = original.inspection
)

fun TechnicalReport.toResponse() = TechnicalReportResponse(
    id = id!!,
    name = name,
    rows = technicalReportRows.map { it.toResponse() }.sortedBy { it.id },
)