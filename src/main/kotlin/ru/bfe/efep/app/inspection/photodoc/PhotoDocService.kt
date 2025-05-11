package ru.bfe.efep.app.inspection.photodoc

import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import ru.bfe.efep.app.inspection.InspectionRepository
import ru.bfe.efep.app.material.MaterialRepository
import ru.bfe.efep.app.spot.SpotRepository
import ru.bfe.efep.app.structelem.StructElemRepository

@Service
class PhotoDocService(
    private val photoDocRepository: PhotoDocRepository,
    private val inspectionRepository: InspectionRepository,
    private val spotRepository: SpotRepository,
    private val structElemRepository: StructElemRepository,
    private val materialRepository: MaterialRepository
) {

    fun createPhotoDoc(inspectionId: Long, request: PhotoDocUpdateRequest): PhotoDocResponse {
        return photoDocRepository.save(
            request.toEntity(
                spotRepository, structElemRepository, materialRepository, findInspection(inspectionId)
            )
        ).toResponse()
    }

    fun getPhotoDoc(inspectionId: Long, id: Long): PhotoDocResponse {
        return photoDocRepository.findByIdAndInspectionId(
            id = id,
            inspectionId = inspectionId
        )?.toResponse()
            ?: throw notFoundException(
                id = id,
                inspectionId = inspectionId
            )
    }

    fun getAllPhotoDocs(inspectionId: Long): List<PhotoDocResponse> {
        return photoDocRepository.findByInspectionId(inspectionId).map { it.toResponse() }
    }

    fun updatePhotoDoc(
        id: Long,
        inspectionId: Long,
        request: PhotoDocUpdateRequest
    ): PhotoDocResponse {
        if (!photoDocRepository.existsById(id)) {
            throw notFoundException(
                id = id,
                inspectionId = inspectionId
            )
        }

        return photoDocRepository.save(
            request.toEntity(
                spotRepository,
                structElemRepository,
                materialRepository,
                findInspection(inspectionId),
                id
            )
        ).toResponse()
    }

    fun deletePhotoDoc(id: Long) {
        photoDocRepository.deleteById(id)
    }

    private fun findInspection(inspectionId: Long) = inspectionRepository.findById(inspectionId).orElseThrow {
        EntityNotFoundException("Inspection with id $inspectionId not found")
    }
}

private fun notFoundException(inspectionId: Long, id: Long) =
    EntityNotFoundException("PhotoDoc not found with id: $id and inspectionId: $inspectionId")