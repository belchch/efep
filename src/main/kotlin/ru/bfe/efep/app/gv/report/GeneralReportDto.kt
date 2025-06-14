package ru.bfe.efep.app.gv.report

data class GeneralViewReportPhotoResponse(
    val id: Long,
    val source: String,
    val url: String
)

data class GeneralViewReportItemResponse(
    val id: Long,
    val photos: List<GeneralViewReportPhotoResponse>,
    val text: String
)

data class GeneralViewReportRowResponse(
    val id: Long,
    val items: List<GeneralViewReportItemResponse>
)

data class GeneralViewReportResponse(
    val id: Long,
    val rows: List<GeneralViewReportRowResponse>
)

data class GeneralViewReportPhotoUpdateRequest(
    val id: Long,
    val source: String
)

data class GeneralViewReportItemUpdateRequest(
    val id: Long,
    val photos: List<GeneralViewReportPhotoUpdateRequest>,
    val text: String
)

data class GeneralViewReportRowUpdateRequest(
    val id: Long,
    val items: List<GeneralViewReportItemUpdateRequest>
)

data class GeneralViewReportUpdateRequest(
    val id: Long,
    val inspectionId: Long,
    val rows: List<GeneralViewReportRowUpdateRequest>
)