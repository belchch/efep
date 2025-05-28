package ru.bfe.efep.app.inspection.photodoc

import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import ru.bfe.efep.app.defect.DefectRepository
import ru.bfe.efep.app.defect.flaw.FlawRepository
import ru.bfe.efep.app.inspection.InspectionRepository
import ru.bfe.efep.app.material.MaterialRepository
import ru.bfe.efep.app.s3.S3Service
import ru.bfe.efep.app.spot.SpotRepository
import ru.bfe.efep.app.structelem.StructElemRepository

@Service
class PhotoDocService(
    private val photoDocRepository: PhotoDocRepository,
    private val inspectionRepository: InspectionRepository,
    private val spotRepository: SpotRepository,
    private val structElemRepository: StructElemRepository,
    private val materialRepository: MaterialRepository,
    private val flawRepository: FlawRepository,
    private val defectRepository: DefectRepository,
    private val s3Service: S3Service
) {

    fun createPhotoDoc(inspectionId: Long, request: PhotoDocUpdateRequest): PhotoDocResponse {
        return photoDocRepository.save(
            request.toEntity(
                spotRepository, structElemRepository, materialRepository, flawRepository, defectRepository,findInspection(inspectionId)
            )
        ).toResponse(s3Service)
    }

    fun getPhotoDoc(inspectionId: Long, id: Long): PhotoDocResponse {
        return photoDocRepository.findByIdAndInspectionId(
            id = id,
            inspectionId = inspectionId
        )?.toResponse(s3Service)
            ?: throw notFoundException(
                id = id,
                inspectionId = inspectionId
            )
    }

    fun searchPhotoDocs(
        inspectionId: Long,
        spotIds: List<Long?>?,
        structElemIds: List<Long?>?,
        materialIds: List<Long?>?,
        types: List<PhotoDocType?>?
    ): List<PhotoDocResponse> {
        val spec = buildSearchSpecification(
            inspectionId = inspectionId,
            spotIds = spotIds,
            structElemIds = structElemIds,
            materialIds = materialIds,
            types = types
        )
        return photoDocRepository.findAll(spec).map { it.toResponse(s3Service) }
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
                flawRepository,
                defectRepository,
                findInspection(inspectionId),
                id
            )
        ).toResponse(s3Service)
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