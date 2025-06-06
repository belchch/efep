package ru.bfe.efep.app.inspection.photodoc

import jakarta.persistence.EntityNotFoundException
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import ru.bfe.efep.app.defect.DefectRepository
import ru.bfe.efep.app.defect.flaw.FlawRepository
import ru.bfe.efep.app.inspection.InspectionRepository
import ru.bfe.efep.app.inspection.tr.row.TechnicalReportRowRepository
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
    private val technicalReportRowRepository: TechnicalReportRowRepository,
    private val s3Service: S3Service
) {

    fun createPhotoDoc(inspectionId: Long, request: PhotoDocUpdateRequest): PhotoDocResponse {
        return photoDocRepository.save(
            request.toEntity(
                spotRepository, structElemRepository, materialRepository, flawRepository, defectRepository, technicalReportRowRepository,findInspection(inspectionId)
            )
        ).toResponse()
    }

    fun generatePreSignedUrls(inspectionId: Long) {
        val photoDocs = photoDocRepository.findByInspectionId(inspectionId)

        for (photoDoc in photoDocs) {
            photoDoc.urls = photoDoc.sources.map { s3Service.generateDownloadUrl(it)}
        }

        photoDocRepository.saveAll(photoDocs)
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
        return photoDocRepository.findAll(spec).map { it.toResponse() }.sortedBy { it.id }
    }

    fun updatePhotoDoc(
        id: Long,
        inspectionId: Long,
        request: PhotoDocUpdateRequest
    ): PhotoDocResponse {
        val current = photoDocRepository.findByIdAndInspectionId(id, inspectionId)
        if (current == null) {
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
                technicalReportRowRepository,
                findInspection(inspectionId),
                current
            )
        ).toResponse()
    }

    fun deletePhotoDoc(id: Long) {
        photoDocRepository.deleteById(id)
    }

    private fun findInspection(inspectionId: Long) = inspectionRepository.findById(inspectionId).orElseThrow {
        EntityNotFoundException("Inspection with id $inspectionId not found")
    }

    @Transactional
    fun groupPhotoDocs(inspectionId: Long, unionRequest: PhotoDocUnionRequest) {
        val target = photoDocRepository.findById(unionRequest.targetId).get()
        val others = photoDocRepository.findAllById(unionRequest.otherIds)
        target.sources = target.sources + others.flatMap { it.sources }
        photoDocRepository.deleteAllById(others.map { it.id })
        generatePreSignedUrls(inspectionId)
    }

    @Transactional
    fun ungroupPhotoDocs(inspectionId: Long, photoDocId: Long) {
        val photoDoc = photoDocRepository.findById(photoDocId).get()

        val ungrouped = photoDoc.sources.drop(1).map {
            photoDoc.copy(id = null, sources = listOf(it))
        }

        photoDocRepository.saveAll(ungrouped)
        generatePreSignedUrls(inspectionId)
    }
}

private fun notFoundException(inspectionId: Long, id: Long) =
    EntityNotFoundException("PhotoDoc not found with id: $id and inspectionId: $inspectionId")