package ru.bfe.efep.app.defect.flaw

import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service

@Service
class FlawService(
    private val flawRepository: FlawRepository
) {

    fun createFlaw(request: FlawUpdateRequest): FlawResponse {
        return flawRepository.save(request.toEntity()).toResponse()
    }

    fun getFlaw(id: Long): FlawResponse {
        return flawRepository.findById(id).map { it.toResponse() }
            .orElseThrow { notFoundException(id) }
    }

    fun getAllFlaws(): List<FlawResponse> {
        return flawRepository.findAll().map { it.toResponse() }
    }

    fun updateFlaw(
        id: Long,
        request: FlawUpdateRequest
    ): FlawResponse {
        if (!flawRepository.existsById(id)) {
            throw notFoundException(id)
        }

        return flawRepository.save(request.toEntity(id)).toResponse()
    }

    fun deleteFlaw(id: Long) {
        flawRepository.deleteById(id)
    }
}

private fun notFoundException(id: Long) = EntityNotFoundException("Flaw not found with id: $id")