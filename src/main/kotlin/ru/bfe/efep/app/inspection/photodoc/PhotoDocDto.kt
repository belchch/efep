package ru.bfe.efep.app.inspection.photodoc

import jakarta.persistence.EntityNotFoundException
import ru.bfe.efep.app.defect.DefectRepository
import ru.bfe.efep.app.defect.DefectResponse
import ru.bfe.efep.app.defect.flaw.FlawRepository
import ru.bfe.efep.app.defect.flaw.FlawResponse
import ru.bfe.efep.app.defect.flaw.toResponse
import ru.bfe.efep.app.defect.toResponse
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
    val defectInfo: DefectInfoUpdateRequest?,
)

data class DefectInfoUpdateRequest(
    val materialId: Long?,
    val structElemId: Long?,
    val flawId: Long?,
    val defectId: Long?,
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
    val flaw: FlawResponse?,
    val defect: DefectResponse?
)

fun PhotoDocUpdateRequest.toEntity(
    spotRepository: SpotRepository,
    structElemRepository: StructElemRepository,
    materialRepository: MaterialRepository,
    flawRepository: FlawRepository,
    defectRepository: DefectRepository,
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
            flaw = defectInfo?.flawId?.let { flawRepository.findByIdOrThrow(it) },
            defect = defectInfo?.defectId?.let { defectRepository.findByIdOrThrow(it) },
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
    structElem = structElem?.toResponse(),
    flaw = flaw?.toResponse(),
    defect = defect?.toResponse(),
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

private fun FlawRepository.findByIdOrThrow(id: Long) = findById(id).orElseThrow {
    EntityNotFoundException("Material with id $id not found")
}

private fun DefectRepository.findByIdOrThrow(id: Long) = findById(id).orElseThrow {
    EntityNotFoundException("Material with id $id not found")
}