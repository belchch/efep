package ru.bfe.efep.app.cases

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/cases")
class CaseController(
    private val caseService: CaseService
) {

    @PostMapping
    fun createCase(@RequestBody request: CaseUpdateRequest): ResponseEntity<CaseResponse> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(caseService.createCase(request))
    }

    @GetMapping("/{id}")
    fun getCase(@PathVariable id: Long): ResponseEntity<CaseResponse> {
        return ResponseEntity.ok(caseService.getCase(id))
    }

    @GetMapping
    fun getAllCases(): ResponseEntity<List<CaseResponse>> {
        return ResponseEntity.ok(caseService.getAllCases())
    }

    @PutMapping("/{id}")
    fun updateCase(
        @PathVariable id: Long,
        @RequestBody request: CaseUpdateRequest
    ): ResponseEntity<CaseResponse> {
        return ResponseEntity.ok(caseService.updateCase(id, request))
    }

    @PatchMapping("/{id}")
    fun patchCase(
        @PathVariable id: Long,
        @RequestBody request: CasePatchRequest
    ): ResponseEntity<CaseResponse> {
        return ResponseEntity.ok(caseService.patchCase(id, request))
    }

    @DeleteMapping("/{id}")
    fun deleteCase(@PathVariable id: Long): ResponseEntity<Void> {
        caseService.deleteCase(id)
        return ResponseEntity.noContent().build()
    }
}