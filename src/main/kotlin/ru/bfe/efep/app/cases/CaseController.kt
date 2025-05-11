package ru.bfe.efep.app.cases

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.Instant

@RestController
@RequestMapping("/api/cases")
class CaseController(
    private val caseService: CaseService
) {

    @PostMapping
    fun createCase(@RequestBody request: CaseCreateRequest): ResponseEntity<CaseResponse> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(caseService.createCase(request))
    }

    @GetMapping("/{id}")
    fun getCase(@PathVariable id: Long): ResponseEntity<CaseResponse> {
        return ResponseEntity.ok(caseService.getCase(id))
    }

    @GetMapping
    fun searchCases(
        @RequestParam number: String?,
        @RequestParam("status") statuses: Set<CaseStatus>?,
        @RequestParam("priority") priorities: Set<CasePriority>?,
        @RequestParam("facilityAddress") facilityAddress: String?,
        @RequestParam("courtId") courtIds: List<Long?>?,
        @RequestParam("judgeId") judgeIds: List<Long?>?,
        @RequestParam("companyId") companyIds: List<Long>?,
        @RequestParam("regionId") regionIds: List<Long>?,
        @RequestParam("createdAtFrom") createdAtFrom: Instant?,
        @RequestParam("createdAtTo") createdAtTo: Instant?,
        @RequestParam("courtIdIsNull") courtIdIsNull: Boolean?,
        @RequestParam("judgeIdIsNull") judgeIdIsNull: Boolean?
    ): ResponseEntity<List<CaseResponse>> {
        fun <T> appendNull(list: List<T>?, hasNull: Boolean?) =
            if (hasNull == true) listOf(null) + (list ?: emptyList()) else list

        val cases = caseService.searchCases(
            number = number,
            statuses = statuses,
            priorities = priorities,
            facilityAddress = facilityAddress,
            courtIds = appendNull(courtIds, courtIdIsNull),
            judgeIds = appendNull(judgeIds, judgeIdIsNull),
            companyIds = companyIds,
            regionIds = regionIds,
            createdAtFrom = createdAtFrom,
            createdAtTo = createdAtTo
        )

        return ResponseEntity.ok(cases)
    }

    @PutMapping("/{id}")
    fun updateCase(
        @PathVariable id: Long,
        @RequestBody request: CaseUpdateRequest
    ): ResponseEntity<CaseResponse> {
        return ResponseEntity.ok(caseService.updateCase(id, request))
    }

    @PatchMapping("/{id}")
    fun patchCase(
        @PathVariable id: Long,
        @RequestBody request: CasePatchRequest
    ): ResponseEntity<CaseResponse> {
        return ResponseEntity.ok(caseService.patchCase(id, request))
    }

    @DeleteMapping("/{id}")
    fun deleteCase(@PathVariable id: Long): ResponseEntity<Void> {
        caseService.deleteCase(id)
        return ResponseEntity.noContent().build()
    }
}