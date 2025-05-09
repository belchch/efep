package ru.bfe.efep.app.company

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

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