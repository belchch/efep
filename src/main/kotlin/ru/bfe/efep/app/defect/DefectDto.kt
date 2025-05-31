package ru.bfe.efep.app.defect

import jakarta.persistence.EntityNotFoundException
import ru.bfe.efep.app.defect.flaw.FlawRepository
import ru.bfe.efep.app.defect.flaw.FlawResponse
import ru.bfe.efep.app.defect.flaw.toResponse
import ru.bfe.efep.app.material.MaterialRepository
import ru.bfe.efep.app.material.MaterialResponse
import ru.bfe.efep.app.material.toResponse
import ru.bfe.efep.app.standard.Standard
import ru.bfe.efep.app.standard.StandardRepository
import ru.bfe.efep.app.standard.StandardResponse
import ru.bfe.efep.app.standard.toResponse
import ru.bfe.efep.app.structelem.StructElemRepository
import ru.bfe.efep.app.structelem.StructElemResponse
import ru.bfe.efep.app.structelem.toResponse

data class DefectUpdateRequest(
    var id: Long?,
    val template: String,
    val standardId: Long,
    val structElemId: Long,
    val materialId: Long?,
    var flawId: Long?,
)

data class DefectResponse(
    val id: Long?,
    val template: String,
    val standard: StandardResponse,
    val structElem: StructElemResponse,
    val material: MaterialResponse?,
    var flaw: FlawResponse?
)

fun DefectUpdateRequest.toEntity(
    structElemRepository: StructElemRepository,
    materialRepository: MaterialRepository,
    flawRepository: FlawRepository,
    standardRepository: StandardRepository
): Defect {
    return Defect(
        id = id,
        template = template,
        standard = standardRepository.findById(standardId)
            .orElseThrow { EntityNotFoundException("Standard not found with id: $standardId") },
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
    standard = standard.toResponse(),
    structElem = structElem.toResponse(),
    material = material?.toResponse(),
    flaw = flaw?.toResponse()
)