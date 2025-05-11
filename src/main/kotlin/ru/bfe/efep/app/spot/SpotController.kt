package ru.bfe.efep.app.spot

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/spots")
class SpotController(
    private val spotService: SpotService
) {

    @PostMapping
    fun createSpot(@RequestBody request: SpotUpdateRequest): ResponseEntity<SpotResponse> {
        val createdSpot = spotService.createSpot(request)
        return ResponseEntity(createdSpot, HttpStatus.CREATED)
    }

    @GetMapping("/{id}")
    fun getSpot(@PathVariable id: Long): ResponseEntity<SpotResponse> {
        val spot = spotService.getSpot(id)
        return ResponseEntity.ok(spot)
    }

    @GetMapping
    fun getAllSpots(): ResponseEntity<List<SpotResponse>> {
        val spots = spotService.getAllSpots()
        return ResponseEntity.ok(spots)
    }

    @PutMapping("/{id}")
    fun updateSpot(
        @PathVariable id: Long,
        @RequestBody request: SpotUpdateRequest
    ): ResponseEntity<SpotResponse> {
        val updatedSpot = spotService.updateSpot(id, request)
        return ResponseEntity.ok(updatedSpot)
    }

    @DeleteMapping("/{id}")
    fun deleteSpot(@PathVariable id: Long): ResponseEntity<Void> {
        spotService.deleteSpot(id)
        return ResponseEntity.noContent().build()
    }
}