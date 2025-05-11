package ru.bfe.efep.app.region

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

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
        val region = regionService.getRegion(id)
        return ResponseEntity.ok(region)
    }

    @GetMapping
    fun getAllRegions(): ResponseEntity<List<RegionResponse>> {
        val regions = regionService.getAllRegions()
        return ResponseEntity.ok(regions)
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