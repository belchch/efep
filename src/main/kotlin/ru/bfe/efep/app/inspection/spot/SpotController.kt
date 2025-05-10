package ru.bfe.efep.app.inspection.spot

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
        val court = spotService.getSpot(id)
        return ResponseEntity.ok(court)
    }

    @GetMapping
    fun getAllSpots(): ResponseEntity<List<SpotResponse>> {
        val courts = spotService.getAllSpots()
        return ResponseEntity.ok(courts)
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