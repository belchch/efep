package ru.bfe.efep.app.inspection.tr

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
@RequestMapping("/api/technical-reports")
class TechnicalReportController(
    private val technicalReportService: TechnicalReportService
) {

    @PostMapping
    fun createTechnicalReport(@RequestBody request: TechnicalReportCreateRequest): ResponseEntity<TechnicalReportResponse> {
        val createdTechnicalReport = technicalReportService.createTechnicalReport(request)
        return ResponseEntity(createdTechnicalReport, HttpStatus.CREATED)
    }

    @GetMapping("/{id}")
    fun getTechnicalReport(@PathVariable id: Long): ResponseEntity<TechnicalReportResponse> {
        val technicalReport = technicalReportService.getTechnicalReport(id)
        return ResponseEntity.ok(technicalReport)
    }

    @GetMapping
    fun getAllTechnicalReports(): ResponseEntity<List<TechnicalReportResponse>> {
        val technicalReports = technicalReportService.getAllTechnicalReports()
        return ResponseEntity.ok(technicalReports)
    }

    @PutMapping("/{id}")
    fun updateTechnicalReport(
        @PathVariable id: Long,
        @RequestBody request: TechnicalReportUpdateRequest
    ): ResponseEntity<TechnicalReportResponse> {
        val updatedTechnicalReport = technicalReportService.updateTechnicalReport(id, request)
        return ResponseEntity.ok(updatedTechnicalReport)
    }

    @DeleteMapping("/{id}")
    fun deleteTechnicalReport(@PathVariable id: Long): ResponseEntity<Void> {
        technicalReportService.deleteTechnicalReport(id)
        return ResponseEntity.noContent().build()
    }
}