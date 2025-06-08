package ru.bfe.efep.app.defect.report.photo

data class DefectReportPhotoResponse(
    val id: Long,
    val source: String,
    val url: String?,
    val used: Boolean
)

fun DefectReportPhoto.toResponse() = DefectReportPhotoResponse(
    id = id!!,
    source = source,
    url = url,
    used = used
)