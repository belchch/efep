package ru.bfe.efep.app.inspection

import jakarta.persistence.EntityNotFoundException
import ru.bfe.efep.app.cases.CaseRepository
import ru.bfe.efep.app.user.UserRepository
import java.time.Instant

data class InspectionUpdateRequest(
    val address: String,
    val performedDate: Instant?,
    val performedById: Long?,
    val caseId: Long?
)


data class InspectionResponse(
    val id: Long,
    val address: String,
    val performedDate: Instant?,
    val performedById: Long?,
    val caseId: Long?
)

fun InspectionUpdateRequest.toEntity(userRepository: UserRepository, caseRepository: CaseRepository, id: Long? = null): Inspection {
    return Inspection(
        id = id,
        address = address,
        performedDate = performedDate,
        performedBy = performedById?.let { userRepository.findById(it).orElseThrow { EntityNotFoundException("User not found with id: $performedById") } },
        case = caseId?.let { caseRepository.findById(it).orElseThrow { EntityNotFoundException("Case not found with id: $caseId") } },
    )
}

fun Inspection.toResponse() = InspectionResponse(
    id = id!!,
    address = address,
    performedDate = performedDate,
    performedBy?.id,
    caseId = case?.id,
)