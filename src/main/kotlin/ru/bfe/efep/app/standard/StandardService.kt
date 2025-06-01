package ru.bfe.efep.app.standard

import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service

@Service
class StandardService(
    private val standardRepository: StandardRepository
) {

    fun createStandard(request: StandardUpdateRequest): StandardResponse {
        return standardRepository.save(request.toEntity()).toResponse()
    }

    fun getStandard(id: Long): StandardResponse {
        return standardRepository.findById(id).map { it.toResponse() }
            .orElseThrow { notFoundException(id) }
    }

    fun getAllStandards(): List<StandardResponse> {
        return standardRepository.findAll().map { it.toResponse() }
    }

    fun updateStandard(
        id: Long,
        request: StandardUpdateRequest
    ): StandardResponse {
        if (!standardRepository.existsById(id)) {
            throw notFoundException(id)
        }

        return standardRepository.save(request.toEntity(id)).toResponse()
    }

    fun deleteStandard(id: Long) {
        standardRepository.deleteById(id)
    }
}

private fun notFoundException(id: Long) = EntityNotFoundException("Standard not found with id: $id")