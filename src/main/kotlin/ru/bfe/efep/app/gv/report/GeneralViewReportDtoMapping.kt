package ru.bfe.efep.app.gv.report

import ru.bfe.efep.app.inspection.Inspection

fun GeneralViewReportPhoto.toResponse() = GeneralViewReportPhotoResponse(
    id = id!!,
    source = source,
    url = url!!
)

fun GeneralViewReportItem.toResponse() = GeneralViewReportItemResponse(
    id = id!!,
    photos = photos.map { it.toResponse() },
    text = text
)

fun GeneralViewReportRow.toResponse() = GeneralViewReportRowResponse(
    id = id!!,
    items = items.map { it.toResponse() }
)

fun GeneralViewReport.toResponse() = GeneralViewReportResponse(
    id = id!!,
    inspectionId = inspection.id!!,
    rows = rows.map { it.toResponse() }
)

fun GeneralViewReportPhotoUpdateRequest.toEntity(item: GeneralViewReportItem) = GeneralViewReportPhoto(
    id = id,
    source = source,
    url = url,
    item = item
)

fun GeneralViewReportItemUpdateRequest.toEntity(row: GeneralViewReportRow) = GeneralViewReportItem(
    id = id,
    text = text,
    row = row
)

fun GeneralViewReportRowUpdateRequest.toEntity(report: GeneralViewReport) = GeneralViewReportRow(
    id = id,
    report = report
)

fun GeneralViewReportUpdateRequest.toEntity(inspection: Inspection) = GeneralViewReport(
    id = id,
    inspection = inspection
)

