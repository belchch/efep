package ru.bfe.efep.app.inspection.tr

import jakarta.persistence.EntityNotFoundException
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import ru.bfe.efep.app.inspection.InspectionRepository
import ru.bfe.efep.app.inspection.photodoc.PhotoDocRepository
import ru.bfe.efep.app.standard.StandardRepository

@Service
class TechnicalReportService(
    private val technicalReportRepository: TechnicalReportRepository,
    private val inspectionRepository: InspectionRepository,
    private val standardRepository: StandardRepository,
    private val photoDocRepository: PhotoDocRepository,
    private val technicalReportRowRepository: TechnicalReportRowRepository
) {

    fun createTechnicalReport(inspectionId: Long, request: TechnicalReportCreateRequest): TechnicalReportResponse {
        val inspection = inspectionRepository.findById(inspectionId).orElseThrow { EntityNotFoundException("Inspection not found with id: $inspectionId") }
        return technicalReportRepository.save(request.toEntity(inspection)).toResponse()
    }

    fun findByInspectionId(inspectionId: Long): TechnicalReportResponse? {
        return technicalReportRepository.findByInspectionId(inspectionId)?.toResponse()
    }

    @Transactional
    fun updateTechnicalReport(
        inspectionId: Long,
        request: TechnicalReportUpdateRequest
    ): TechnicalReportResponse {
        val existing = technicalReportRepository.findByInspectionId(inspectionId) ?: throw notFoundException(inspectionId)
        val new = request.toEntity(existing, standardRepository, photoDocRepository)

        new.technicalReportRows.forEach {
            technicalReportRowRepository.save(it)
        }

        return technicalReportRepository.save(new).toResponse()
    }

    fun deleteTechnicalReport(inspectionId: Long) {
        technicalReportRepository.deleteByInspectionId(inspectionId)
    }
}

private fun notFoundException(id: Long) = EntityNotFoundException("TechnicalReport not found with inspectionId: $id")