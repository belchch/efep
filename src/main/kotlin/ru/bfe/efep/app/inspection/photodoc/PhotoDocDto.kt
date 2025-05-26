package ru.bfe.efep.app.inspection.photodoc

import jakarta.persistence.EntityNotFoundException
import ru.bfe.efep.app.inspection.Inspection
import ru.bfe.efep.app.material.MaterialRepository
import ru.bfe.efep.app.material.MaterialResponse
import ru.bfe.efep.app.material.toResponse
import ru.bfe.efep.app.s3.S3Service
import ru.bfe.efep.app.spot.SpotRepository
import ru.bfe.efep.app.spot.SpotResponse
import ru.bfe.efep.app.spot.toResponse
import ru.bfe.efep.app.structelem.StructElemRepository
import ru.bfe.efep.app.structelem.StructElemResponse
import ru.bfe.efep.app.structelem.toResponse

data class PhotoDocUpdateRequest(
    val source: String,
    val spotId: Long?,
    val type: PhotoDocType?,
    val defectInfo: DefectInfoUpdateRequest?
)

data class DefectInfoUpdateRequest(
    val materialId: Long?,
    val structElemId: Long?,
)

data class PhotoDocResponse(
    val id: Long,
    val url: String,
    val source: String,
    val spot: SpotResponse?,
    val type: PhotoDocType?,
    val defectInfo: DefectInfoResponse?
)

data class DefectInfoResponse(
    val structElem: StructElemResponse?,
    val material: MaterialResponse?,
)

fun PhotoDocUpdateRequest.toEntity(
    spotRepository: SpotRepository,
    structElemRepository: StructElemRepository,
    materialRepository: MaterialRepository,
    inspection: Inspection,
    id: Long? = null,
) = PhotoDoc(
    id = id,
    source = source,
    inspection = inspection,
    spot = spotId?.let { spotRepository.findByIdOrThrow(it) },
    defectInfo = ifDefect(type) {
        DefectInfo(
            material = defectInfo?.materialId?.let { materialRepository.findByIdOrThrow(it) },
            structElem = defectInfo?.structElemId?.let { structElemRepository.findByIdOrThrow(it) },
        )
    },
    type = type,
)

private fun <T> ifDefect(type: PhotoDocType?, produce: () -> T): T? {
    return if (type == PhotoDocType.DEFECT) {
        produce()
    } else {
        null
    }
}

fun PhotoDoc.toResponse(s3Service: S3Service) = PhotoDocResponse(
    id = id!!,
    source = source,
    spot = spot?.toResponse(),
    type = type,
    defectInfo = defectInfo?.toResponse(),
    url = s3Service.generateDownloadUrl(source)
)

fun DefectInfo.toResponse() = DefectInfoResponse(
    material = material?.toResponse(),
    structElem = structElem?.toResponse()
)

private fun SpotRepository.findByIdOrThrow(id: Long) = findById(id).orElseThrow {
    EntityNotFoundException("Spot with id $id not found")
}

private fun StructElemRepository.findByIdOrThrow(id: Long) = findById(id).orElseThrow {
    EntityNotFoundException("StructElem with id $id not found")
}

private fun MaterialRepository.findByIdOrThrow(id: Long) = findById(id).orElseThrow {
    EntityNotFoundException("Material with id $id not found")
}