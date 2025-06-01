package ru.bfe.efep.app.inspection.tr

import jakarta.persistence.EntityNotFoundException
import ru.bfe.efep.app.inspection.Inspection
import ru.bfe.efep.app.inspection.photodoc.PhotoDocRepository
import ru.bfe.efep.app.inspection.photodoc.PhotoDocResponse
import ru.bfe.efep.app.inspection.photodoc.toResponse
import ru.bfe.efep.app.standard.StandardRepository
import ru.bfe.efep.app.standard.StandardResponse
import ru.bfe.efep.app.standard.toResponse

data class TechnicalReportResponse(
    val id: Long,
    val name: String,
    val rows: List<TechnicalReportRowResponse>,
)

data class TechnicalReportCreateRequest(
    val name: String,
)

data class TechnicalReportRowResponse(
    val id: Long?,
    val description: String,
    val standard: StandardResponse,
    val photoDoc: PhotoDocResponse?,
)

data class TechnicalReportRowUpdateRequest(
    val description: String,
    val standardId: Long,
    val photoDocId: Long?
)

data class TechnicalReportUpdateRequest(
    val name: String,
    val rows: List<TechnicalReportRowUpdateRequest>
)

fun TechnicalReportCreateRequest.toEntity(inspection: Inspection) = TechnicalReport(
    name = name,
    inspection = inspection,
)

fun TechnicalReportRowUpdateRequest.toEntity(standardRepository: StandardRepository, photoDocRepository: PhotoDocRepository) = TechnicalReportRow(
    description = description,
    standard = standardRepository.findById(standardId).orElseThrow { EntityNotFoundException("Standard not found with id: $standardId") },
    photoDoc = photoDocId?.let {
        photoDocRepository.findById(it).orElseThrow { EntityNotFoundException("PhotoDoc not found with id: $it") }
    }
)

fun TechnicalReportUpdateRequest.toEntity(
    original: TechnicalReport,
    standardRepository: StandardRepository,
    photoDocRepository: PhotoDocRepository
) = TechnicalReport(
    id = original.id,
    name = name,
    inspection = original.inspection,
    technicalReportRows = rows.map { it.toEntity(standardRepository, photoDocRepository) }.toMutableList(),
)

fun TechnicalReport.toResponse() = TechnicalReportResponse(
    id = id!!,
    name = name,
    rows = technicalReportRows.map { row ->
        TechnicalReportRowResponse(
            id = row.id,
            description = row.description,
            standard = row.standard.toResponse(),
            photoDoc = row.photoDoc?.toResponse()
        )
    }
)