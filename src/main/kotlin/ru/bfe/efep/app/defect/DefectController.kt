package ru.bfe.efep.app.defect

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
@RequestMapping("/api/defects")
class DefectController(
    private val defectService: DefectService
) {

    @PostMapping
    fun createDefect(@RequestBody request: DefectUpdateRequest): ResponseEntity<DefectResponse> {
        val createdDefect = defectService.createDefect(request)
        return ResponseEntity(createdDefect, HttpStatus.CREATED)
    }

    @GetMapping("/{id}")
    fun getDefect(@PathVariable id: Long): ResponseEntity<DefectResponse> {
        val material = defectService.getDefect(id)
        return ResponseEntity.ok(material)
    }

    @GetMapping
    fun getAllDefects(): ResponseEntity<List<DefectResponse>> {
        val materials = defectService.getAllDefects()
        return ResponseEntity.ok(materials)
    }

    @PutMapping("/{id}")
    fun updateDefect(
        @PathVariable id: Long,
        @RequestBody request: DefectUpdateRequest
    ): ResponseEntity<DefectResponse> {
        val updatedDefect = defectService.updateDefect(id, request)
        return ResponseEntity.ok(updatedDefect)
    }

    @DeleteMapping("/{id}")
    fun deleteDefect(@PathVariable id: Long): ResponseEntity<Void> {
        defectService.deleteDefect(id)
        return ResponseEntity.noContent().build()
    }
}