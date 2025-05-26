package ru.bfe.efep.app.defect.flaw

data class FlawResponse(
    val id: Long?,
    val name: String?,
)

fun Flaw.toResponse() = FlawResponse(
    id = id,
    name = name,
)