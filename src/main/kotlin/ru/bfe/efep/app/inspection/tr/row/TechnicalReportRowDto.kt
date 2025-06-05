package ru.bfe.efep.app.inspection.tr.row

import jakarta.persistence.EntityNotFoundException
import ru.bfe.efep.app.inspection.photodoc.PhotoDoc
import ru.bfe.efep.app.inspection.photodoc.PhotoDocRepository
import ru.bfe.efep.app.inspection.photodoc.PhotoDocResponse
import ru.bfe.efep.app.inspection.photodoc.toResponse
import ru.bfe.efep.app.inspection.tr.TechnicalReportRepository
import ru.bfe.efep.app.standard.StandardRepository
import ru.bfe.efep.app.standard.StandardResponse
import ru.bfe.efep.app.standard.toResponse

data class TechnicalReportRowResponse(
    val id: Long?,
    val description: String,
    val standard: StandardResponse,
    val photoDoc: PhotoDocResponse?,
    val technicalReportId: Long?,
    val used: Boolean
)

data class TechnicalReportRowUpdateRequest(
    val description: String,
    val standardId: Long,
    val photoDocId: Long?,
    val technicalReportId: Long,
)

fun TechnicalReportRowUpdateRequest.toEntity(
    technicalReportRepository: TechnicalReportRepository,
    standardRepository: StandardRepository,
    photoDocRepository: PhotoDocRepository,
    id: Long? = null
) = TechnicalReportRow(
    id = id,
    description = description,
    standard = standardRepository.findById(standardId)
        .orElseThrow { EntityNotFoundException("Standard not found with id: $standardId") },
    photoDoc = photoDocId?.let {
        photoDocRepository.findById(it).orElseThrow { EntityNotFoundException("PhotoDoc not found with id: $it") }
    },
    technicalReport = technicalReportRepository.findById(technicalReportId)
        .orElseThrow { EntityNotFoundException("TechnicalReport not found with id: $technicalReportId") }
)

fun TechnicalReportRow.toResponse(photoDocs: List<PhotoDoc>) = TechnicalReportRowResponse(
    id = id,
    description = description,
    standard = standard.toResponse(),
    photoDoc = photoDoc?.toResponse(),
    technicalReportId = technicalReport.id,
    used = photoDocs.any { it.defectInfo?.technicalReportRow?.id == id }
)