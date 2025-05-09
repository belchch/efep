package ru.bfe.efep.app.court

import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service

@Service
class CourtService(
    private val courtRepository: CourtRepository
) {

    fun createCourt(request: CourtCreateRequest): CourtResponse {
        return courtRepository.save(request.toEntity()).toResponse()
    }

    fun getCourt(id: Long): CourtResponse {
        return courtRepository.findById(id).map { it.toResponse() }
            .orElseThrow { notFoundException(id) }
    }

    fun getAllCourts(): List<CourtResponse> {
        return courtRepository.findAll().map { it.toResponse() }
    }

    fun updateCourt(
        id: Long,
        request: CourtUpdateRequest
    ): CourtResponse {
        if (!courtRepository.existsById(id)) {
            notFoundException(id)
        }

        return courtRepository.save(request.toEntity(id)).toResponse()
    }

    fun deleteCourt(id: Long) {
        courtRepository.deleteById(id)
    }
}

private fun notFoundException(id: Long) = EntityNotFoundException("Court not found with id: $id")