package ru.bfe.efep.app.spot

import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service

@Service
class SpotService(
    private val spotRepository: SpotRepository
) {

    fun createSpot(request: SpotUpdateRequest): SpotResponse {
        return spotRepository.save(request.toEntity()).toResponse()
    }

    fun getSpot(id: Long): SpotResponse {
        return spotRepository.findById(id).map { it.toResponse() }
            .orElseThrow { notFoundException(id) }
    }

    fun getAllSpots(): List<SpotResponse> {
        return spotRepository.findAll().map { it.toResponse() }
    }

    fun updateSpot(
        id: Long,
        request: SpotUpdateRequest
    ): SpotResponse {
        if (!spotRepository.existsById(id)) {
            throw notFoundException(id)
        }

        return spotRepository.save(request.toEntity(id)).toResponse()
    }

    fun deleteSpot(id: Long) {
        spotRepository.deleteById(id)
    }
}

private fun notFoundException(id: Long) = EntityNotFoundException("Spot not found with id: $id")