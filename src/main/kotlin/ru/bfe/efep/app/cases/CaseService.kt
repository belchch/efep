package ru.bfe.efep.app.cases

import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import ru.bfe.efep.app.company.CompanyRepository
import ru.bfe.efep.app.court.CourtRepository
import ru.bfe.efep.app.judge.JudgeRepository
import ru.bfe.efep.app.region.RegionRepository
import ru.bfe.efep.app.user.UserRepository

@Service
class CaseService(
    private val caseRepository: CaseRepository,
    private val companyRepository: CompanyRepository,
    private val courtRepository: CourtRepository,
    private val judgeRepository: JudgeRepository,
    private val regionRepository: RegionRepository,
    private val userRepository: UserRepository,
) {

    fun createCase(request: CaseUpdateRequest): CaseResponse {
        return caseRepository.save(request.toEntity(
            courtRepository = courtRepository,
            judgeRepository = judgeRepository,
            companyRepository = companyRepository,
            regionRepository = regionRepository,
            userRepository = userRepository,
        )).toResponse()
    }

    fun getCase(id: Long): CaseResponse {
        return caseRepository.findById(id).map { it.toResponse() }
            .orElseThrow { notFoundException(id) }
    }

    fun getAllCases(): List<CaseResponse> {
        return caseRepository.findAll().map { it.toResponse() }
    }

    fun updateCase(
        id: Long,
        request: CaseUpdateRequest
    ): CaseResponse {
        if (!caseRepository.existsById(id)) {
            notFoundException(id)
        }

        return caseRepository.save(request.toEntity(
            id = id,
            courtRepository = courtRepository,
            judgeRepository = judgeRepository,
            companyRepository = companyRepository,
            regionRepository = regionRepository,
            userRepository = userRepository,
        )).toResponse()
    }

    fun patchCase(
        id: Long,
        request: CasePatchRequest
    ): CaseResponse {
        val case = caseRepository.findById(id).orElseThrow { notFoundException(id) }

        return caseRepository.save(request.mergeWithEntity(
            existing = case,
            courtRepository = courtRepository,
            judgeRepository = judgeRepository,
            companyRepository = companyRepository,
            regionRepository = regionRepository,
        )).toResponse()
    }

    fun deleteCase(id: Long) {
        caseRepository.deleteById(id)
    }
}

private fun notFoundException(id: Long) = EntityNotFoundException("Case not found with id: $id")