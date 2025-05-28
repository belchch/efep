package ru.bfe.efep.app.defect.flaw

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/flaws")
class FlawController(
    private val flawService: FlawService
) {

    @PostMapping
    fun createFlaw(@RequestBody request: FlawUpdateRequest): ResponseEntity<FlawResponse> {
        val createdFlaw = flawService.createFlaw(request)
        return ResponseEntity(createdFlaw, HttpStatus.CREATED)
    }

    @GetMapping("/{id}")
    fun getFlaw(@PathVariable id: Long): ResponseEntity<FlawResponse> {
        val flaw = flawService.getFlaw(id)
        return ResponseEntity.ok(flaw)
    }

    @GetMapping
    fun getAllFlaws(): ResponseEntity<List<FlawResponse>> {
        val flaws = flawService.getAllFlaws()
        return ResponseEntity.ok(flaws)
    }

    @PutMapping("/{id}")
    fun updateFlaw(
        @PathVariable id: Long,
        @RequestBody request: FlawUpdateRequest
    ): ResponseEntity<FlawResponse> {
        val updatedFlaw = flawService.updateFlaw(id, request)
        return ResponseEntity.ok(updatedFlaw)
    }

    @DeleteMapping("/{id}")
    fun deleteFlaw(@PathVariable id: Long): ResponseEntity<Void> {
        flawService.deleteFlaw(id)
        return ResponseEntity.noContent().build()
    }
}