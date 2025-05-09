package ru.bfe.efep.app.region

import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service

@Service
class RegionService(
    private val regionRepository: RegionRepository
) {

    fun createRegion(request: RegionCreateRequest): RegionResponse {
        return regionRepository.save(request.toEntity()).toResponse()
    }

    fun getRegion(id: Long): RegionResponse {
        return regionRepository.findById(id).map { it.toResponse() }
            .orElseThrow { notFoundException(id) }
    }

    fun getAllRegions(): List<RegionResponse> {
        return regionRepository.findAll().map { it.toResponse() }
    }

    fun updateRegion(
        id: Long,
        request: RegionUpdateRequest
    ): RegionResponse {
        if (!regionRepository.existsById(id)) {
            notFoundException(id)
        }

        return regionRepository.save(request.toEntity(id)).toResponse()
    }

    fun deleteRegion(id: Long) {
        regionRepository.deleteById(id)
    }
}

private fun notFoundException(id: Long) = EntityNotFoundException("Region not found with id: $id")