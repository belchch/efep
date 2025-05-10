package ru.bfe.efep.app.inspection

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
@RequestMapping("/api/inspections")
class InspectionController(
    private val inspectionService: InspectionService
) {

    @PostMapping
    fun createInspection(@RequestBody request: InspectionUpdateRequest): ResponseEntity<InspectionResponse> {
        val createdInspection = inspectionService.createInspection(request)
        return ResponseEntity(createdInspection, HttpStatus.CREATED)
    }

    @GetMapping("/{id}")
    fun getInspection(@PathVariable id: Long): ResponseEntity<InspectionResponse> {
        val court = inspectionService.getInspection(id)
        return ResponseEntity.ok(court)
    }

    @GetMapping
    fun getAllInspections(): ResponseEntity<List<InspectionResponse>> {
        val courts = inspectionService.getAllInspections()
        return ResponseEntity.ok(courts)
    }

    @PutMapping("/{id}")
    fun updateInspection(
        @PathVariable id: Long,
        @RequestBody request: InspectionUpdateRequest
    ): ResponseEntity<InspectionResponse> {
        val updatedInspection = inspectionService.updateInspection(id, request)
        return ResponseEntity.ok(updatedInspection)
    }

    @DeleteMapping("/{id}")
    fun deleteInspection(@PathVariable id: Long): ResponseEntity<Void> {
        inspectionService.deleteInspection(id)
        return ResponseEntity.noContent().build()
    }
}