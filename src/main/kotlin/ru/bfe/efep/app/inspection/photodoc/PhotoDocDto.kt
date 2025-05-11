package ru.bfe.efep.app.inspection.photodoc

import jakarta.persistence.EntityNotFoundException
import ru.bfe.efep.app.inspection.Inspection
import ru.bfe.efep.app.material.MaterialRepository
import ru.bfe.efep.app.material.MaterialResponse
import ru.bfe.efep.app.material.toResponse
import ru.bfe.efep.app.spot.SpotRepository
import ru.bfe.efep.app.spot.SpotResponse
import ru.bfe.efep.app.spot.toResponse
import ru.bfe.efep.app.structelem.StructElemRepository
import ru.bfe.efep.app.structelem.StructElemResponse
import ru.bfe.efep.app.structelem.toResponse

data class PhotoDocUpdateRequest(
    val source: String,
    val spotId: Long?,
    val structElemId: Long?,
    val materialId: Long?,
    val type: PhotoDocType?,
)

data class PhotoDocResponse(
    val id: Long,
    val source: String,
    val spot: SpotResponse?,
    val structElem: StructElemResponse?,
    val material: MaterialResponse?,
    val type: PhotoDocType?
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
    spot = spotId?.let { spotRepository.findByIdOrThrow(spotId) },
    structElem = ifDefect(type) {
        structElemId?.let { structElemRepository.findByIdOrThrow(it) }
    },
    material = ifDefect(type) {
        materialId?.let { materialRepository.findByIdOrThrow(it) }
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

fun PhotoDoc.toResponse() = PhotoDocResponse(
    id = id!!,
    source = source,
    spot = spot?.toResponse(),
    structElem = structElem?.toResponse(),
    material = material?.toResponse(),
    type = type,
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