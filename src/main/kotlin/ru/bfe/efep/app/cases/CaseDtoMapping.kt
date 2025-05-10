package ru.bfe.efep.app.cases

import jakarta.persistence.EntityNotFoundException
import ru.bfe.efep.app.company.Company
import ru.bfe.efep.app.company.CompanyRepository
import ru.bfe.efep.app.company.toResponse
import ru.bfe.efep.app.court.Court
import ru.bfe.efep.app.court.CourtRepository
import ru.bfe.efep.app.court.toResponse
import ru.bfe.efep.app.judge.Judge
import ru.bfe.efep.app.judge.JudgeRepository
import ru.bfe.efep.app.judge.toResponse
import ru.bfe.efep.app.region.Region
import ru.bfe.efep.app.region.RegionRepository
import ru.bfe.efep.app.region.toResponse
import ru.bfe.efep.app.user.User
import ru.bfe.efep.app.user.UserRepository
import ru.bfe.efep.app.user.toResponse


fun CaseCreateRequest.toEntity(
    courtRepository: CourtRepository,
    judgeRepository: JudgeRepository,
    companyRepository: CompanyRepository,
    regionRepository: RegionRepository,
    userRepository: UserRepository
): Case {
    return Case(
        number = number,
        status = status,
        priority = priority,
        facilityAddress = facilityAddress,
        court = courtId?.let { courtRepository.findByIdOrThrow(it) },
        judge = judgeId?.let { judgeRepository.findByIdOrThrow(it) },
        company = companyRepository.findByIdOrThrow(companyId),
        region = regionRepository.findByIdOrThrow(regionId),
        createdBy = userRepository.findByIdOrThrow(createdById),
        createdDate = createdDate
    )
}

fun CaseUpdateRequest.toEntity(
    existing: Case,
    courtRepository: CourtRepository,
    judgeRepository: JudgeRepository,
    companyRepository: CompanyRepository,
    regionRepository: RegionRepository
): Case {
    return Case(
        id = existing.id,
        number = number,
        status = status,
        priority = priority,
        facilityAddress = facilityAddress,
        court = courtId?.let { courtRepository.findByIdOrThrow(it) },
        judge = judgeId?.let { judgeRepository.findByIdOrThrow(it) },
        company = companyRepository.findByIdOrThrow(companyId),
        region = regionRepository.findByIdOrThrow(regionId),
        createdBy = existing.createdBy,
        createdDate = existing.createdDate
    )
}

fun CasePatchRequest.mergeWithEntity(
    existing: Case,
    courtRepository: CourtRepository,
    judgeRepository: JudgeRepository,
    companyRepository: CompanyRepository,
    regionRepository: RegionRepository
): Case {
    return existing.copy(
        number = number ?: existing.number,
        status = status ?: existing.status,
        priority = priority ?: existing.priority,
        facilityAddress = facilityAddress ?: existing.facilityAddress,
        court = courtId?.let { courtRepository.findByIdOrThrow(it) } ?: existing.court,
        judge = judgeId?.let { judgeRepository.findByIdOrThrow(it) } ?: existing.judge,
        company = companyId?.let { companyRepository.findByIdOrThrow(it) } ?: existing.company,
        region = regionId?.let { regionRepository.findByIdOrThrow(it) } ?: existing.region,
        createdBy = existing.createdBy
    )
}

fun Case.toResponse() = CaseResponse(
    id = id!!,
    number = number,
    status = status,
    priority = priority,
    facilityAddress = facilityAddress,
    court = court?.toResponse(),
    judge = judge?.toResponse(),
    company = company.toResponse(),
    region = region.toResponse(),
    createdBy = createdBy.toResponse()
)

fun CourtRepository.findByIdOrThrow(id: Long): Court {
    return findById(id).orElseThrow { EntityNotFoundException("Court not found with id: $id") }
}

fun JudgeRepository.findByIdOrThrow(id: Long): Judge {
    return findById(id).orElseThrow { EntityNotFoundException("Judge not found with id: $id") }
}

fun CompanyRepository.findByIdOrThrow(id: Long): Company {
    return findById(id).orElseThrow { EntityNotFoundException("Company not found with id: $id") }
}

fun RegionRepository.findByIdOrThrow(id: Long): Region {
    return findById(id).orElseThrow { EntityNotFoundException("Region not found with id: $id") }
}

fun UserRepository.findByIdOrThrow(id: Long): User {
    return findById(id).orElseThrow { EntityNotFoundException("User not found with id: $id") }
}