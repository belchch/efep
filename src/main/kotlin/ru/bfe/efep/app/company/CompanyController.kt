package ru.bfe.efep.app.company

import jakarta.persistence.EntityNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/companies")
class CompanyController(
    private val companyService: CompanyService
) {
    @PostMapping
    fun createCompany(@RequestBody body: CompanyCreateRequest): ResponseEntity<CompanyResponse> {
        return ResponseEntity.ok(companyService.createCompany(body))
    }

    @GetMapping("/{id}")
    fun getCompany(@PathVariable id: Long): ResponseEntity<CompanyResponse> {
        return ResponseEntity.ok(companyService.getCompany(id))
    }

    @GetMapping
    fun getAllCompanies(): ResponseEntity<List<CompanyResponse>> {
        return ResponseEntity.ok(companyService.getAllCompanies())
    }

    @PutMapping("/{id}")
    fun updateCompany(
        @PathVariable id: Long,
        @RequestBody body: CompanyUpdateRequest
    ): ResponseEntity<CompanyResponse> {
        return ResponseEntity.ok(companyService.updateCompany(id, body))
    }

    @DeleteMapping("/{id}")
    fun deleteCompany(@PathVariable id: Long): ResponseEntity<Void> {
        companyService.deleteCompany(id)
        return ResponseEntity.noContent().build()
    }
}