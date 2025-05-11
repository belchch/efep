package ru.bfe.efep.app.court

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/courts")
class CourtController(
    private val courtService: CourtService
) {

    @PostMapping
    fun createCourt(@RequestBody request: CourtCreateRequest): ResponseEntity<CourtResponse> {
        val createdCourt = courtService.createCourt(request)
        return ResponseEntity(createdCourt, HttpStatus.CREATED)
    }

    @GetMapping("/{id}")
    fun getCourt(@PathVariable id: Long): ResponseEntity<CourtResponse> {
        val court = courtService.getCourt(id)
        return ResponseEntity.ok(court)
    }

    @GetMapping
    fun getAllCourts(): ResponseEntity<List<CourtResponse>> {
        val courts = courtService.getAllCourts()
        return ResponseEntity.ok(courts)
    }

    @PutMapping("/{id}")
    fun updateCourt(
        @PathVariable id: Long,
        @RequestBody request: CourtUpdateRequest
    ): ResponseEntity<CourtResponse> {
        val updatedCourt = courtService.updateCourt(id, request)
        return ResponseEntity.ok(updatedCourt)
    }

    @DeleteMapping("/{id}")
    fun deleteCourt(@PathVariable id: Long): ResponseEntity<Void> {
        courtService.deleteCourt(id)
        return ResponseEntity.noContent().build()
    }
}