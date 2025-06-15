package ru.bfe.efep.app.gv.report

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

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
    fun updateReport(@RequestBody request: GeneralViewReportUpdateRequest): ResponseEntity<GeneralViewReportResponse> {
        val response = generalViewReportService.updateReport(request)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/gallery")
    fun getGallery(@RequestParam inspectionId: Long): List<GeneralViewReportGalleryGroup>? {
        return generalViewReportService.getGallery(inspectionId)
    }

}