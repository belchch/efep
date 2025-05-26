package ru.bfe.efep.app.defect

import jakarta.persistence.EntityNotFoundException
import ru.bfe.efep.app.defect.flaw.FlawRepository
import ru.bfe.efep.app.defect.flaw.FlawResponse
import ru.bfe.efep.app.defect.flaw.toResponse
import ru.bfe.efep.app.material.MaterialRepository
import ru.bfe.efep.app.material.MaterialResponse
import ru.bfe.efep.app.material.toResponse
import ru.bfe.efep.app.structelem.StructElemRepository
import ru.bfe.efep.app.structelem.StructElemResponse
import ru.bfe.efep.app.structelem.toResponse

data class DefectUpdateRequest(
    var id: Long?,
    val template: String,
    val standard: String,
    val structElemId: Long,
    val materialId: Long?,
    var flawId: Long?
)

data class DefectResponse(
    val id: Long?,
    val template: String,
    val standard: String,
    val structElem: StructElemResponse,
    val material: MaterialResponse?,
    var flaw: FlawResponse?
)

fun DefectUpdateRequest.toEntity(
    structElemRepository: StructElemRepository,
    materialRepository: MaterialRepository,
    flawRepository: FlawRepository,
): Defect {
    return Defect(
        id = id,
        template = template,
        standard = standard,
        structElem = structElemRepository.findById(structElemId)
            .orElseThrow { EntityNotFoundException("StructElem not found with id: $structElemId") },
        material = materialId?.let {
            materialRepository.findById(it)
                .orElseThrow { EntityNotFoundException("Material not found with id: $materialId") }
        },
        flaw = flawId?.let {
            flawRepository.findById(it)
                .orElseThrow { EntityNotFoundException("Flaw not found with id: $flawId") }
        }
    )
}

fun Defect.toResponse() = DefectResponse(
    id = id!!,
    template = template,
    standard = standard,
    structElem = structElem.toResponse(),
    material = material?.toResponse(),
    flaw = flaw?.toResponse()
)