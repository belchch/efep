package ru.bfe.efep.app.structelem

import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import ru.bfe.efep.app.inspection.material.MaterialRepository

@Service
class StructElemService(
    private val structElemRepository: StructElemRepository,
    private val materialRepository: MaterialRepository
) {

    fun createStructElem(request: StructElemUpdateRequest): StructElemResponse {
        return structElemRepository.save(request.toEntity(materialRepository)).toResponse()
    }

    fun getStructElem(id: Long): StructElemResponse {
        return structElemRepository.findById(id).map { it.toResponse() }
            .orElseThrow { notFoundException(id) }
    }

    fun getAllStructElems(): List<StructElemResponse> {
        return structElemRepository.findAll().map { it.toResponse() }
    }

    fun updateStructElem(
        id: Long,
        request: StructElemUpdateRequest
    ): StructElemResponse {
        if (!structElemRepository.existsById(id)) {
            throw notFoundException(id)
        }

        return structElemRepository.save(request.toEntity(materialRepository, id)).toResponse()
    }

    fun deleteStructElem(id: Long) {
        structElemRepository.deleteById(id)
    }
}

private fun notFoundException(id: Long) = EntityNotFoundException("StructElem not found with id: $id")