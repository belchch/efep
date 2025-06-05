package ru.bfe.efep.app.inspection.tr.row

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/inspections/{inspectionId}/technical-report-rows")
class TechnicalReportRowController(
    private val technicalReportRowService: TechnicalReportRowService
) {

    @PostMapping
    fun createTechnicalReportRow(@PathVariable inspectionId: Long, @RequestBody request: TechnicalReportRowUpdateRequest): ResponseEntity<TechnicalReportRowResponse> {
        val createdTechnicalReportRow = technicalReportRowService.createTechnicalReportRow(request)
        return ResponseEntity(createdTechnicalReportRow, HttpStatus.CREATED)
    }

    @GetMapping
    fun getAllTechnicalReportRows(@PathVariable inspectionId: Long): ResponseEntity<List<TechnicalReportRowResponse>> {
        val technicalReportRow = technicalReportRowService.getAllTechnicalReportRows(inspectionId)
        return ResponseEntity.ok(technicalReportRow)
    }

    @PutMapping("/{id}")
    fun updateTechnicalReportRow(
        @PathVariable inspectionId: Long,
        @PathVariable id: Long,
        @RequestBody request: TechnicalReportRowUpdateRequest
    ): ResponseEntity<TechnicalReportRowResponse> {
        val updatedTechnicalReportRow = technicalReportRowService.updateTechnicalReportRow(id, inspectionId, request)
        return ResponseEntity.ok(updatedTechnicalReportRow)
    }


    @DeleteMapping("/{id}")
    fun deleteTechnicalReportRow(@PathVariable inspectionId: Long, @PathVariable id: Long): ResponseEntity<Void> {
        technicalReportRowService.deleteTechnicalReportRow(id)
        return ResponseEntity.noContent().build()
    }
}