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
        return technicalReportRowRepository.save(request.toEntity(
            technicalReportRepository,
            standardRepository,
            photoDocRepository
        )).toResponse()
    }

    fun getTechnicalReportRow(id: Long): TechnicalReportRowResponse {
        return technicalReportRowRepository.findById(id).map { it.toResponse() }
            .orElseThrow { notFoundException(id) }
    }

    fun getAllTechnicalReportRows(): List<TechnicalReportRowResponse> {
        return technicalReportRowRepository.findAll().map { it.toResponse() }
    }

    fun updateTechnicalReportRow(
        id: Long,
        request: TechnicalReportRowUpdateRequest
    ): TechnicalReportRowResponse {
        if (!technicalReportRowRepository.existsById(id)) {
            throw notFoundException(id)
        }

        val new = request.toEntity(technicalReportRepository, standardRepository, photoDocRepository, id)
        linkNewPhotoDoc(new)

        return technicalReportRowRepository.save(new).toResponse()
    }

    private fun linkNewPhotoDoc(row: TechnicalReportRow) {
        if (row.photoDoc != null) {
            if (row.photoDoc!!.defectInfo == null) {
                row.photoDoc!!.defectInfo = DefectInfo()
            }

            row.photoDoc!!.defectInfo!!.technicalReportRow = row
            photoDocRepository.save(row.photoDoc!!)
        }
    }

    fun deleteTechnicalReportRow(id: Long) {
        technicalReportRowRepository.deleteById(id)
    }
}

private fun notFoundException(id: Long) = EntityNotFoundException("TechnicalReportRow not found with id: $id")