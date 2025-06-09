package ru.bfe.efep.app.cases

import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import ru.bfe.efep.app.company.CompanyRepository
import ru.bfe.efep.app.court.CourtRepository
import ru.bfe.efep.app.inspection.Inspection
import ru.bfe.efep.app.inspection.InspectionRepository
import ru.bfe.efep.app.judge.JudgeRepository
import ru.bfe.efep.app.region.RegionRepository
import ru.bfe.efep.app.user.User
import ru.bfe.efep.app.user.UserRepository
import java.time.Instant

@Service
class CaseService(
    private val caseRepository: CaseRepository,
    private val companyRepository: CompanyRepository,
    private val courtRepository: CourtRepository,
    private val judgeRepository: JudgeRepository,
    private val regionRepository: RegionRepository,
    private val userRepository: UserRepository,
    private val inspectionRepository: InspectionRepository
) {

    fun createCase(request: CaseCreateRequest, user: User): CaseResponse {
        val created = caseRepository.save(
            request.toEntity(
                courtRepository = courtRepository,
                judgeRepository = judgeRepository,
                companyRepository = companyRepository,
                regionRepository = regionRepository,
                userRepository = userRepository,
                systemProps = CaseCreateSystemProps(
                    createdUserId = user.id!!,
                    createdDate = Instant.now(),
                )
            )
        )

        val inspection = inspectionRepository.save(Inspection(
            case = created,
            address = request.facilityAddress
        ))

        created.inspections.add(inspection)
        return created.toResponse()
    }

    fun getCase(id: Long): CaseResponse {
        return caseRepository.findById(id).map { it.toResponse() }
            .orElseThrow { notFoundException(id) }
    }

    fun searchCases(
        search: String?,
        statuses: Set<CaseStatus>?,
        priorities: Set<CasePriority>?,
        courtIds: List<Long?>?,
        judgeIds: List<Long?>?,
        companyIds: List<Long>?,
        regionIds: List<Long>?,
        createdAtFrom: Instant?,
        createdAtTo: Instant?
    ): List<CaseResponse> {
        val spec = buildCaseSpecification(
            search = search,
            statuses = statuses,
            priorities = priorities,
            courtIds = courtIds,
            judgeIds = judgeIds,
            companyIds = companyIds,
            regionIds = regionIds,
            createdAtFrom = createdAtFrom,
            createdAtTo = createdAtTo
        )

        return caseRepository.findAll(spec).map { it.toResponse() }.sortedByDescending { it.createdAt }
    }

    fun updateCase(
        id: Long,
        request: CaseUpdateRequest
    ): CaseResponse {
        val case = caseRepository.findById(id).orElseThrow {
            notFoundException(id)
        }

        return caseRepository.save(
            request.toEntity(
                case,
                courtRepository = courtRepository,
                judgeRepository = judgeRepository,
                companyRepository = companyRepository,
                regionRepository = regionRepository,
                inspectionRepository = inspectionRepository,
            )
        ).toResponse()
    }

    fun patchCase(
        id: Long,
        request: CasePatchRequest
    ): CaseResponse {
        val case = caseRepository.findById(id).orElseThrow { notFoundException(id) }

        return caseRepository.save(
            request.mergeWithEntity(
                existing = case,
                courtRepository = courtRepository,
                judgeRepository = judgeRepository,
                companyRepository = companyRepository,
                regionRepository = regionRepository,
                inspectionRepository = inspectionRepository,
            )
        ).toResponse()
    }

    fun deleteCase(id: Long) {
        caseRepository.deleteById(id)
    }
}

private fun notFoundException(id: Long) = EntityNotFoundException("Case not found with id: $id")