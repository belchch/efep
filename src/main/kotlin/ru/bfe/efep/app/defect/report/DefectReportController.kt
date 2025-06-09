package ru.bfe.efep.app.defect.report

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.bfe.efep.app.defect.report.photo.DefectReportPhoto
import ru.bfe.efep.app.defect.report.photo.DefectReportPhotoResponse

@RestController
@RequestMapping("/api/defect-report")
class DefectReportController(
    private val defectReportService: DefectReportService
) {
    @PostMapping("/build")
    fun buildReport(@RequestParam inspectionId: Long, @RequestParam useTechnicalReport: Boolean): ResponseEntity<*> {
        defectReportService.buildReport(inspectionId, useTechnicalReport)
        return ResponseEntity.ok().build<Any>()
    }

    @GetMapping
    fun getReport(@RequestParam inspectionId: Long): ResponseEntity<DefectReportResponse?> {
        val response = defectReportService.getReport(inspectionId)
        return ResponseEntity(response, HttpStatus.OK)
    }

    @PostMapping("/move-spot")
    fun moveSpot(@RequestParam spotId: Long, fromIndex: Int, toIndex: Int): ResponseEntity<*> {
        defectReportService.moveSpot(spotId, fromIndex, toIndex)
        return ResponseEntity.ok().build<Any>()
    }

    @PostMapping("/move-struct-elem")
    fun moveStructElem(@RequestParam structElemId: Long, fromIndex: Int, toIndex: Int): ResponseEntity<*> {
        defectReportService.moveStructElem(structElemId, fromIndex, toIndex)
        return ResponseEntity.ok().build<Any>()
    }

    @PostMapping("/move-row")
    fun moveRow(@RequestParam rowId: Long, fromIndex: Int, toIndex: Int): ResponseEntity<*> {
        defectReportService.moveRow(rowId, fromIndex, toIndex)
        return ResponseEntity.ok().build<Any>()
    }

    @PostMapping("/use-photo")
    fun usePhoto(@RequestParam photoId: Long, @RequestParam use: Boolean, @RequestParam scope: Int): ResponseEntity<List<DefectReportPhotoResponse>> {
        val result = defectReportService.usePhoto(photoId, use, scope)
        return ResponseEntity.ok().body(result)
    }
}