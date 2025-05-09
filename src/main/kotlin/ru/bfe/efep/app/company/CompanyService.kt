package ru.bfe.efep.app.company

import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service

@Service
class CompanyService(
    private val companyRepository: CompanyRepository
) {

    fun createCompany(request: CompanyCreateRequest): CompanyResponse {
        return companyRepository.save(request.toEntity()).toResponse()
    }

    fun getCompany(id: Long): CompanyResponse {
        return companyRepository.findById(id).map { it.toResponse() }
            .orElseThrow { notFoundException(id) }
    }

    fun getAllCompanies(): List<CompanyResponse> {
        return companyRepository.findAll().map { it.toResponse() }
    }

    fun updateCompany(
        id: Long,
        request: CompanyUpdateRequest
    ): CompanyResponse {
        if (!companyRepository.existsById(id)) {
            notFoundException(id)
        }

        return companyRepository.save(request.toEntity(id)).toResponse()
    }

    fun deleteCompany(id: Long) {
        companyRepository.deleteById(id)
    }
}

private fun notFoundException(id: Long) = EntityNotFoundException("Company not found with id: $id")