package ru.bfe.efep.app.inspection.tr

import jakarta.persistence.EntityNotFoundException
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
) {

    fun createTechnicalReport(request: TechnicalReportCreateRequest): TechnicalReportResponse {
        return technicalReportRepository.save(request.toEntity(inspectionRepository)).toResponse()
    }

    fun getTechnicalReport(id: Long): TechnicalReportResponse {
        return technicalReportRepository.findById(id).map { it.toResponse() }
            .orElseThrow { notFoundException(id) }
    }

    fun getAllTechnicalReports(): List<TechnicalReportResponse> {
        return technicalReportRepository.findAll().map { it.toResponse() }
    }

    fun updateTechnicalReport(
        id: Long,
        request: TechnicalReportUpdateRequest
    ): TechnicalReportResponse {
        val existsing = technicalReportRepository.findById(id).orElseThrow { notFoundException(id) }

        return technicalReportRepository.save(request.toEntity(existsing, standardRepository, photoDocRepository)).toResponse()
    }

    fun deleteTechnicalReport(id: Long) {
        technicalReportRepository.deleteById(id)
    }
}

private fun notFoundException(id: Long) = EntityNotFoundException("TechnicalReport not found with id: $id")