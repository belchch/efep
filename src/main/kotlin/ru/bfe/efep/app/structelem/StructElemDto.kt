package ru.bfe.efep.app.structelem

import ru.bfe.efep.app.material.MaterialRepository
import ru.bfe.efep.app.material.MaterialResponse
import ru.bfe.efep.app.material.toResponse


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