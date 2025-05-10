package ru.bfe.efep.app.inspection.material

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
@RequestMapping("/api/materials")
class MaterialController(
    private val materialService: MaterialService
) {

    @PostMapping
    fun createMaterial(@RequestBody request: MaterialUpdateRequest): ResponseEntity<MaterialResponse> {
        val createdMaterial = materialService.createMaterial(request)
        return ResponseEntity(createdMaterial, HttpStatus.CREATED)
    }

    @GetMapping("/{id}")
    fun getMaterial(@PathVariable id: Long): ResponseEntity<MaterialResponse> {
        val court = materialService.getMaterial(id)
        return ResponseEntity.ok(court)
    }

    @GetMapping
    fun getAllMaterials(): ResponseEntity<List<MaterialResponse>> {
        val courts = materialService.getAllMaterials()
        return ResponseEntity.ok(courts)
    }

    @PutMapping("/{id}")
    fun updateMaterial(
        @PathVariable id: Long,
        @RequestBody request: MaterialUpdateRequest
    ): ResponseEntity<MaterialResponse> {
        val updatedMaterial = materialService.updateMaterial(id, request)
        return ResponseEntity.ok(updatedMaterial)
    }

    @DeleteMapping("/{id}")
    fun deleteMaterial(@PathVariable id: Long): ResponseEntity<Void> {
        materialService.deleteMaterial(id)
        return ResponseEntity.noContent().build()
    }
}