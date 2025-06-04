package ru.bfe.efep.app.inspection.tr

import jakarta.persistence.EntityNotFoundException
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import ru.bfe.efep.app.inspection.InspectionRepository

@Service
class TechnicalReportService(
    private val technicalReportRepository: TechnicalReportRepository,
    private val inspectionRepository: InspectionRepository
) {

    fun createTechnicalReport(inspectionId: Long, request: TechnicalReportCreateRequest): TechnicalReportResponse {
        val inspection = inspectionRepository.findById(inspectionId)
            .orElseThrow { EntityNotFoundException("Inspection not found with id: $inspectionId") }
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
        val existing =
            technicalReportRepository.findByInspectionId(inspectionId) ?: throw notFoundException(inspectionId)
        val new = request.toEntity(existing)
        return technicalReportRepository.save(new).toResponse()
    }

    fun deleteTechnicalReport(inspectionId: Long) {
        technicalReportRepository.deleteByInspectionId(inspectionId)
    }
}

private fun notFoundException(id: Long) = EntityNotFoundException("TechnicalReport not found with inspectionId: $id")