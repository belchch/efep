package ru.bfe.efep.app.inspection.spot.room

import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service

@Service
class RoomService(
    private val roomRepository: RoomRepository
) {

    fun createRoom(request: RoomUpdateRequest): RoomResponse {
        return roomRepository.save(request.toEntity()).toResponse()
    }

    fun getRoom(id: Long): RoomResponse {
        return roomRepository.findById(id).map { it.toResponse() }
            .orElseThrow { notFoundException(id) }
    }

    fun getAllRooms(): List<RoomResponse> {
        return roomRepository.findAll().map { it.toResponse() }
    }

    fun updateRoom(
        id: Long,
        request: RoomUpdateRequest
    ): RoomResponse {
        if (!roomRepository.existsById(id)) {
            throw notFoundException(id)
        }

        return roomRepository.save(request.toEntity(id)).toResponse()
    }

    fun deleteRoom(id: Long) {
        roomRepository.deleteById(id)
    }
}

private fun notFoundException(id: Long) = EntityNotFoundException("Room not found with id: $id")