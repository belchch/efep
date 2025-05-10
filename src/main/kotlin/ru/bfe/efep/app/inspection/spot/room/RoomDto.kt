package ru.bfe.efep.app.inspection.spot.room

data class RoomUpdateRequest(
    val name: String
)

data class RoomResponse(
    val id: Long,
    val name: String
)

fun RoomUpdateRequest.toEntity(id: Long? = null) = Room(
    id = id,
    name = name
)

fun Room.toResponse() = RoomResponse(
    id = id!!,
    name = name
)