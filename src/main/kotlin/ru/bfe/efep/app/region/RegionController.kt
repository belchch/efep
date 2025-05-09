package ru.bfe.efep.app.region

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
@RequestMapping("/api/regions")
class RegionController(
    private val regionService: RegionService
) {

    @PostMapping
    fun createRegion(@RequestBody request: RegionCreateRequest): ResponseEntity<RegionResponse> {
        val createdRegion = regionService.createRegion(request)
        return ResponseEntity(createdRegion, HttpStatus.CREATED)
    }

    @GetMapping("/{id}")
    fun getRegion(@PathVariable id: Long): ResponseEntity<RegionResponse> {
        val court = regionService.getRegion(id)
        return ResponseEntity.ok(court)
    }

    @GetMapping
    fun getAllRegions(): ResponseEntity<List<RegionResponse>> {
        val courts = regionService.getAllRegions()
        return ResponseEntity.ok(courts)
    }

    @PutMapping("/{id}")
    fun updateRegion(
        @PathVariable id: Long,
        @RequestBody request: RegionUpdateRequest
    ): ResponseEntity<RegionResponse> {
        val updatedRegion = regionService.updateRegion(id, request)
        return ResponseEntity.ok(updatedRegion)
    }

    @DeleteMapping("/{id}")
    fun deleteRegion(@PathVariable id: Long): ResponseEntity<Void> {
        regionService.deleteRegion(id)
        return ResponseEntity.noContent().build()
    }
}