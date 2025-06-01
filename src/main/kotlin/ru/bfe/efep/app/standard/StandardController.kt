package ru.bfe.efep.app.standard

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/standards")
class StandardController(
    private val standardService: StandardService
) {

    @PostMapping
    fun createStandard(@RequestBody request: StandardUpdateRequest): ResponseEntity<StandardResponse> {
        val createdStandard = standardService.createStandard(request)
        return ResponseEntity(createdStandard, HttpStatus.CREATED)
    }

    @GetMapping("/{id}")
    fun getStandard(@PathVariable id: Long): ResponseEntity<StandardResponse> {
        val standard = standardService.getStandard(id)
        return ResponseEntity.ok(standard)
    }

    @GetMapping
    fun getAllStandards(): ResponseEntity<List<StandardResponse>> {
        val standards = standardService.getAllStandards()
        return ResponseEntity.ok(standards)
    }

    @PutMapping("/{id}")
    fun updateStandard(
        @PathVariable id: Long,
        @RequestBody request: StandardUpdateRequest
    ): ResponseEntity<StandardResponse> {
        val updatedStandard = standardService.updateStandard(id, request)
        return ResponseEntity.ok(updatedStandard)
    }

    @DeleteMapping("/{id}")
    fun deleteStandard(@PathVariable id: Long): ResponseEntity<Void> {
        standardService.deleteStandard(id)
        return ResponseEntity.noContent().build()
    }
}