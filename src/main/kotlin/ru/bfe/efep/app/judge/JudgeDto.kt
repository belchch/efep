package ru.bfe.efep.app.judge

import jakarta.persistence.EntityNotFoundException
import ru.bfe.efep.app.court.CourtRepository
import ru.bfe.efep.app.court.CourtResponse
import ru.bfe.efep.app.court.toResponse

data class JudgeCreateRequest(
    val courtId: Long,
    val firstName: String,
    val middleName: String?,
    val lastName: String
)

data class JudgeUpdateRequest(
    val courtId: Long?,
    val firstName: String?,
    val middleName: String?,
    val lastName: String?
)

data class JudgeResponse(
    val id: Long,
    val court: CourtResponse,
    val firstName: String,
    val middleName: String?,
    val lastName: String
)

fun JudgeCreateRequest.toEntity(courtRepository: CourtRepository): Judge {
    val court = courtRepository.findById(courtId)
        .orElseThrow { EntityNotFoundException("Court not found with id: $courtId") }

    return Judge(
        court = court,
        firstName = firstName,
        middleName = middleName,
        lastName = lastName
    )
}

fun JudgeUpdateRequest.toEntity(id: Long, courtRepository: CourtRepository): Judge {
    val court = courtRepository.findById(courtId!!)
        .orElseThrow { EntityNotFoundException("Court not found with id: $courtId") }

    return Judge(
        id = id,
        court = court,
        firstName = firstName ?: throw IllegalArgumentException("firstName cannot be null for update"),
        middleName = middleName,
        lastName = lastName ?: throw IllegalArgumentException("lastName cannot be null for update"),
    )
}

fun Judge.toResponse() = JudgeResponse(
    id = id!!,
    court = court.toResponse(),
    firstName = firstName,
    middleName = middleName,
    lastName = lastName
)