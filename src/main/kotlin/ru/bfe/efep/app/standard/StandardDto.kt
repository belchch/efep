package ru.bfe.efep.app.standard

data class StandardResponse (
    val id: Long? = null,
    val name: String,
    val description: String
)

fun Standard.toResponse() = StandardResponse(
    id = id,
    name = name,
    description = description
)