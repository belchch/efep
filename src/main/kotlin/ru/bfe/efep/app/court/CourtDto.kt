package ru.bfe.efep.app.court

data class CourtCreateRequest(
    val name: String,
    val postalCode: String?,
    val region: String?
)

data class CourtUpdateRequest(
    val name: String?,
    val postalCode: String?,
    val region: String?
)

data class CourtResponse(
    val id: Long,
    val name: String,
    val postalCode: String?,
    val region: String?
)

fun CourtCreateRequest.toEntity() = Court(
    name = name,
    postalCode = postalCode,
    region = region
)

fun CourtUpdateRequest.toEntity(id: Long) = Court(
    id = id,
    name = name ?: throw IllegalArgumentException("name cannot be null for update"),
    postalCode = postalCode,
    region = region
)

fun Court.toResponse() = CourtResponse(
    id = id!!,
    name = name,
    postalCode = postalCode,
    region = region
)