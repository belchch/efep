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
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/technical-report-rows")
class TechnicalReportRowController(
    private val technicalReportRowService: TechnicalReportRowService
) {

    @PostMapping
    fun createTechnicalReportRow(@RequestBody request: TechnicalReportRowUpdateRequest): ResponseEntity<TechnicalReportRowResponse> {
        val createdTechnicalReportRow = technicalReportRowService.createTechnicalReportRow(request)
        return ResponseEntity(createdTechnicalReportRow, HttpStatus.CREATED)
    }

    @GetMapping("/{id}")
    fun getTechnicalReportRow(@PathVariable id: Long): ResponseEntity<TechnicalReportRowResponse> {
        val technicalReportRow = technicalReportRowService.getTechnicalReportRow(id)
        return ResponseEntity.ok(technicalReportRow)
    }

    @GetMapping
    fun getAllTechnicalReportRows(): ResponseEntity<List<TechnicalReportRowResponse>> {
        val technicalReportRow = technicalReportRowService.getAllTechnicalReportRows()
        return ResponseEntity.ok(technicalReportRow)
    }

    @PutMapping("/{id}")
    fun updateTechnicalReportRow(
        @PathVariable id: Long,
        @RequestBody request: TechnicalReportRowUpdateRequest
    ): ResponseEntity<TechnicalReportRowResponse> {
        val updatedTechnicalReportRow = technicalReportRowService.updateTechnicalReportRow(id, request)
        return ResponseEntity.ok(updatedTechnicalReportRow)
    }

    @DeleteMapping("/{id}")
    fun deleteTechnicalReportRow(@PathVariable id: Long): ResponseEntity<Void> {
        technicalReportRowService.deleteTechnicalReportRow(id)
        return ResponseEntity.noContent().build()
    }
}