package ru.bfe.efep.app.gv.report

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/general-view-report")
class GeneralViewReportController(
    private val generalViewReportService: GeneralViewReportService
) {

    @PostMapping("/build")
    fun buildReport(@RequestParam inspectionId: Long): ResponseEntity<*> {
        generalViewReportService.buildReport(inspectionId)
        return ResponseEntity.ok().build<Any>()
    }

    @GetMapping
    fun getReport(@RequestParam inspectionId: Long): ResponseEntity<GeneralViewReportResponse> {
        val report = generalViewReportService.getReport(inspectionId)
        return ResponseEntity.ok().body(report)
    }

    @PutMapping
    fun updateReport(@RequestBody request: GeneralViewReportUpdateRequest): ResponseEntity<*> {
        generalViewReportService.updateReport(request)
        return ResponseEntity.ok().build<Any>()
    }
}