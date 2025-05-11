package ru.bfe.efep.app.material

data class MaterialUpdateRequest(
    val name: String
)

data class MaterialResponse(
    val id: Long,
    val name: String
)

fun MaterialUpdateRequest.toEntity(id: Long? = null) = Material(
    id = id,
    name = name
)

fun Material.toResponse() = MaterialResponse(
    id = id!!,
    name = name
)