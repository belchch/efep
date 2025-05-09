package ru.bfe.efep.app.region

data class RegionCreateRequest(
    val code: String,
    val name: String
)

data class RegionUpdateRequest(
    val code: String?,
    val name: String?
)

data class RegionResponse(
    val id: Long,
    val code: String,
    val name: String
)

fun RegionCreateRequest.toEntity() = Region(
    code = code,
    name = name
)

fun RegionUpdateRequest.toEntity(id: Long) = Region(
    id = id,
    name = name ?: throw IllegalArgumentException("name cannot be null for update"),
    code = code ?: throw IllegalArgumentException("code cannot be null for update"),
)

fun Region.toResponse() = RegionResponse(
    id = id!!,
    name = name,
    code = code,
)