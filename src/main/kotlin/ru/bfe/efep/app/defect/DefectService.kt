package ru.bfe.efep.app.defect

import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import ru.bfe.efep.app.defect.flaw.FlawRepository
import ru.bfe.efep.app.inspection.toResponse
import ru.bfe.efep.app.material.MaterialRepository
import ru.bfe.efep.app.standard.StandardRepository
import ru.bfe.efep.app.structelem.StructElemRepository
import ru.bfe.efep.app.user.UserRepository

@Service
class DefectService(
    private val defectRepository: DefectRepository,
    private val structElemRepository: StructElemRepository,
    private val materialRepository: MaterialRepository,
    private val flawRepository: FlawRepository,
    private val standardRepository: StandardRepository,
) {

    fun createDefect(request: DefectUpdateRequest): DefectResponse {
        return defectRepository.save(request.toEntity(
            structElemRepository = structElemRepository,
            materialRepository = materialRepository,
            flawRepository = flawRepository,
            standardRepository = standardRepository,
        )).toResponse()
    }

    fun getDefect(id: Long): DefectResponse {
        return defectRepository.findById(id).map { it.toResponse() }
            .orElseThrow { notFoundException(id) }
    }

    fun getAllDefects(): List<DefectResponse> {
        return defectRepository.findAll().map { it.toResponse() }
    }

    fun updateDefect(
        id: Long,
        request: DefectUpdateRequest
    ): DefectResponse {
        if (!defectRepository.existsById(id)) {
            notFoundException(id)
        }

        request.id = id

        return defectRepository.save(request.toEntity(
            structElemRepository = structElemRepository,
            materialRepository = materialRepository,
            flawRepository = flawRepository,
            standardRepository = standardRepository,

        )).toResponse()
    }

    fun deleteDefect(id: Long) {
        defectRepository.deleteById(id)
    }
}

private fun notFoundException(id: Long) = EntityNotFoundException("Defect not found with id: $id")