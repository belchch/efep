package ru.bfe.efep.app.inspection.structelem

import ru.bfe.efep.app.inspection.material.MaterialRepository
import ru.bfe.efep.app.inspection.material.MaterialResponse
import ru.bfe.efep.app.inspection.material.toResponse
import kotlin.Long
import kotlin.collections.List

data class StructElemUpdateRequest(
    val name: String,
    val materialIds: List<Long>
)

data class StructElemResponse(
    val id: Long,
    val name: String,
    val materials: List<MaterialResponse>
)

fun StructElemUpdateRequest.toEntity(materialRepository: MaterialRepository, id: Long? = null) = StructElem(
    id = id,
    name = name,
    materials = materialRepository.findAllById(materialIds)
)

fun StructElem.toResponse() = StructElemResponse(
    id = id!!,
    name = name,
    materials = materials.map { it.toResponse() }
)