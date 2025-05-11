package ru.bfe.efep.app.material

import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service

@Service
class MaterialService(
    private val materialRepository: MaterialRepository
) {

    fun createMaterial(request: MaterialUpdateRequest): MaterialResponse {
        return materialRepository.save(request.toEntity()).toResponse()
    }

    fun getMaterial(id: Long): MaterialResponse {
        return materialRepository.findById(id).map { it.toResponse() }
            .orElseThrow { notFoundException(id) }
    }

    fun getAllMaterials(): List<MaterialResponse> {
        return materialRepository.findAll().map { it.toResponse() }
    }

    fun updateMaterial(
        id: Long,
        request: MaterialUpdateRequest
    ): MaterialResponse {
        if (!materialRepository.existsById(id)) {
            throw notFoundException(id)
        }

        return materialRepository.save(request.toEntity(id)).toResponse()
    }

    fun deleteMaterial(id: Long) {
        materialRepository.deleteById(id)
    }
}

private fun notFoundException(id: Long) = EntityNotFoundException("Material not found with id: $id")