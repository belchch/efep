package ru.bfe.efep.app.inspection

import jakarta.persistence.EntityNotFoundException
import ru.bfe.efep.app.user.UserRepository
import java.time.Instant

data class InspectionUpdateRequest(
    val address: String,
    val performedDate: Instant?,
    val performedById: Long?
)


data class InspectionResponse(
    val id: Long,
    val address: String,
    val performedDate: Instant?,
    val performedById: Long?
)

fun InspectionUpdateRequest.toEntity(userRepository: UserRepository, id: Long? = null): Inspection {
    return Inspection(
        id = id,
        address = address,
        performedDate = performedDate,
        performedBy = performedById?.let { userRepository.findById(it).orElseThrow { EntityNotFoundException("User not found with id: $id") } }
    )
}

fun Inspection.toResponse() = InspectionResponse(
    id = id!!,
    address = address,
    performedDate = performedDate,
    performedBy?.id
)