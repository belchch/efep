package ru.bfe.efep.app.inspection

import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import ru.bfe.efep.app.cases.CaseRepository
import ru.bfe.efep.app.user.UserRepository

@Service
class InspectionService(
    private val inspectionRepository: InspectionRepository,
    private val userRepository: UserRepository,
    private val caseRepository: CaseRepository,
) {

    fun createInspection(request: InspectionUpdateRequest): InspectionResponse {
        return inspectionRepository.save(request.toEntity(userRepository, caseRepository)).toResponse()
    }

    fun getInspection(id: Long): InspectionResponse {
        return inspectionRepository.findById(id).map { it.toResponse() }
            .orElseThrow { notFoundException(id) }
    }

    fun getAllInspections(): List<InspectionResponse> {
        return inspectionRepository.findAll().map { it.toResponse() }
    }

    fun getAllByCaseId(caseId: Long): List<InspectionResponse> {
        return inspectionRepository.findAllByCaseId(caseId).map { it.toResponse() }
    }

    fun updateInspection(
        id: Long,
        request: InspectionUpdateRequest
    ): InspectionResponse {
        if (!inspectionRepository.existsById(id)) {
            notFoundException(id)
        }

        return inspectionRepository.save(request.toEntity(userRepository, caseRepository, id)).toResponse()
    }

    fun deleteInspection(id: Long) {
        inspectionRepository.deleteById(id)
    }
}

private fun notFoundException(id: Long) = EntityNotFoundException("Inspection not found with id: $id")