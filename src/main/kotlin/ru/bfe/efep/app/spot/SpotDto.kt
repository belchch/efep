package ru.bfe.efep.app.spot

data class SpotUpdateRequest(
    val name: String
)

data class SpotResponse(
    val id: Long,
    val name: String
)

fun SpotUpdateRequest.toEntity(id: Long? = null) = Spot(
    id = id,
    name = name
)

fun Spot.toResponse() = SpotResponse(
    id = id!!,
    name = name
)