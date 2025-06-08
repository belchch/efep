package ru.bfe.efep.app.defect.report

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/defect-report")
class DefectReportController(
    private val defectReportService: DefectReportService
) {
    @PostMapping("/build")
    fun buildReport(@RequestParam inspectionId: Long): ResponseEntity<*> {
        defectReportService.buildReport(inspectionId)
        return ResponseEntity.ok().build<Any>()
    }

    @GetMapping
    fun getReport(@RequestParam inspectionId: Long): ResponseEntity<DefectReportResponse?> {
        val response = defectReportService.getReport(inspectionId)
        return ResponseEntity(response, HttpStatus.OK)
    }
}