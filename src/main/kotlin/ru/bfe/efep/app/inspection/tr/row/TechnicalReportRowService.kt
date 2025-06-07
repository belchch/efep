package ru.bfe.efep.app.inspection.tr.row

import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import ru.bfe.efep.app.inspection.photodoc.DefectInfo
import ru.bfe.efep.app.inspection.photodoc.PhotoDocRepository
import ru.bfe.efep.app.inspection.tr.TechnicalReportRepository
import ru.bfe.efep.app.standard.StandardRepository

@Service
class TechnicalReportRowService(
    private val technicalReportRowRepository: TechnicalReportRowRepository,
    private val technicalReportRepository: TechnicalReportRepository,
    private val standardRepository: StandardRepository,
    private val photoDocRepository: PhotoDocRepository
) {

    fun createTechnicalReportRow(request: TechnicalReportRowUpdateRequest): TechnicalReportRowResponse {
        return technicalReportRowRepository.save(request.convertToEntityAndLink()).toResponse(emptyList())
    }

    fun getAllTechnicalReportRows(inspectionId: Long): List<TechnicalReportRowResponse> {
        val photoDocs = photoDocRepository.findByInspectionId(inspectionId)
        return technicalReportRepository.findByInspectionId(inspectionId)?.technicalReportRows?.map { it.toResponse(photoDocs) } ?: emptyList()
    }

    fun updateTechnicalReportRow(
        id: Long,
        inspectionId: Long,
        request: TechnicalReportRowUpdateRequest
    ): TechnicalReportRowResponse {
        if (!technicalReportRowRepository.existsById(id)) {
            throw notFoundException(id)
        }

        return technicalReportRowRepository.save(request.convertToEntityAndLink(id)).toResponse(
            photoDocRepository.findByInspectionId(inspectionId)
        )
    }

    private fun linkNewPhotoDoc(row: TechnicalReportRow) {
        if (row.photoDoc != null) {
            if (row.photoDoc!!.defectInfo == null) {
                row.photoDoc!!.defectInfo = DefectInfo()
            }

            row.photoDoc!!.defectInfo!!.technicalReportRow = row
        }
    }

    private fun TechnicalReportRowUpdateRequest.convertToEntityAndLink(id: Long? = null): TechnicalReportRow {
        val entity = toEntity(technicalReportRepository, standardRepository, photoDocRepository, id)
        linkNewPhotoDoc(entity)
        return entity
    }

    fun deleteTechnicalReportRow(id: Long) {
        technicalReportRowRepository.deleteById(id)
    }
}

private fun notFoundException(id: Long) = EntityNotFoundException("TechnicalReportRow not found with id: $id")