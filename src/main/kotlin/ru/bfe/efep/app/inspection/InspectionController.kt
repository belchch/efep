package ru.bfe.efep.app.inspection

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

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
        val inspection = inspectionService.getInspection(id)
        return ResponseEntity.ok(inspection)
    }

    @GetMapping()
    fun getAllInspections(@RequestParam caseId: Long?): ResponseEntity<List<InspectionResponse>> {
        val inspections = if (caseId != null) {
            inspectionService.getAllByCaseId(caseId)
        } else {
            inspectionService.getAllInspections()
        }
        return ResponseEntity.ok(inspections)
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