package ru.bfe.efep.app.standard

data class StandardResponse (
    val id: Long? = null,
    val name: String,
    val description: String
)

data class StandardUpdateRequest (
    val name: String,
    val description: String
)

fun Standard.toResponse() = StandardResponse(
    id = id,
    name = name,
    description = description
)

fun StandardUpdateRequest.toEntity(id: Long? = null) = Standard(
    id = id,
    name = name,
    description = description
)