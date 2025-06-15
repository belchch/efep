package ru.bfe.efep.app.gv.report

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonSetter
import com.fasterxml.jackson.annotation.Nulls

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
    val inspectionId: Long,
    val rows: List<GeneralViewReportRowResponse>
)

data class GeneralViewReportPhotoUpdateRequest(
    val id: Long?,
    val source: String,
    val url: String
)

data class GeneralViewReportItemUpdateRequest(
    val id: Long?,
    val photos: List<GeneralViewReportPhotoUpdateRequest>,
    val text: String
)

data class GeneralViewReportRowUpdateRequest(
    val id: Long?,
    val items: List<GeneralViewReportItemUpdateRequest>
)

data class GeneralViewReportUpdateRequest(
    val id: Long,
    val inspectionId: Long,
    val rows: List<GeneralViewReportRowUpdateRequest>
)

data class GeneralViewReportGalleryGroup(
    val name: String?,
    val photos: List<GeneralViewReportGalleryPhoto>
)

data class GeneralViewReportGalleryPhoto(
    val source: String,
    val url: String
)