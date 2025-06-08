package ru.bfe.efep.app.defect.report.row

import ru.bfe.efep.app.defect.report.photo.DefectReportPhotoResponse
import ru.bfe.efep.app.defect.report.photo.toResponse

data class DefectReportRowResponse(
    val id: Long,
    val technicalReport: String?,
    val defect: String?,
    val standard: String?,
    val photos: MutableList<DefectReportPhotoResponse>,
    val sortOrder: Int,
)

fun DefectReportRow.toResponse() = DefectReportRowResponse(
    id = id!!,
    technicalReport = technicalReport,
    defect = defect,
    standard = standard,
    photos = photos.map { it.toResponse() }.toMutableList(),
    sortOrder = sortOrder
)