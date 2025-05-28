package ru.bfe.efep.app.defect.search

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/defects/search")
class DefectSearchController(
    private val defectSearchService: DefectSearchService
) {
    @PostMapping
    fun search(@RequestBody request: DefectSearchRequest): DefectSearchResponse {
        return defectSearchService.search(request)
    }
}