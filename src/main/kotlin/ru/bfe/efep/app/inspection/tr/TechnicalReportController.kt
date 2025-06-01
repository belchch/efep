package ru.bfe.efep.app.inspection.tr

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/inspections/{inspectionId}/technical-report")
class TechnicalReportController(
    private val technicalReportService: TechnicalReportService
) {

    @PostMapping
    fun createTechnicalReport(@PathVariable inspectionId: Long, @RequestBody request: TechnicalReportCreateRequest): ResponseEntity<TechnicalReportResponse> {
        val createdTechnicalReport = technicalReportService.createTechnicalReport(inspectionId, request)
        return ResponseEntity(createdTechnicalReport, HttpStatus.CREATED)
    }

    @GetMapping
    fun getTechnicalReport(@PathVariable inspectionId: Long): ResponseEntity<TechnicalReportResponse> {
        val response = technicalReportService.findByInspectionId(inspectionId)
        return ResponseEntity.ok(response)
    }


    @PutMapping()
    fun updateTechnicalReport(
        @PathVariable inspectionId: Long,
        @RequestBody request: TechnicalReportUpdateRequest
    ): ResponseEntity<TechnicalReportResponse> {
        val updatedTechnicalReport = technicalReportService.updateTechnicalReport(inspectionId, request)
        return ResponseEntity.ok(updatedTechnicalReport)
    }

    @DeleteMapping()
    fun deleteTechnicalReport(@PathVariable inspectionId: Long): ResponseEntity<Void> {
        technicalReportService.deleteTechnicalReport(inspectionId)
        return ResponseEntity.noContent().build()
    }
}

