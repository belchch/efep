package ru.bfe.efep.app.judge

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/judges")
class JudgeController(
    private val judgeService: JudgeService
) {

    @PostMapping
    fun createJudge(@RequestBody request: JudgeCreateRequest): ResponseEntity<JudgeResponse> {
        val createdJudge = judgeService.createJudge(request)
        return ResponseEntity(createdJudge, HttpStatus.CREATED)
    }

    @GetMapping("/{id}")
    fun getJudge(@PathVariable id: Long): ResponseEntity<JudgeResponse> {
        val judge = judgeService.getJudge(id)
        return ResponseEntity.ok(judge)
    }

    @GetMapping
    fun getAllJudges(): ResponseEntity<List<JudgeResponse>> {
        val judges = judgeService.getAllJudges()
        return ResponseEntity.ok(judges)
    }

    @PutMapping("/{id}")
    fun updateJudge(
        @PathVariable id: Long,
        @RequestBody request: JudgeUpdateRequest
    ): ResponseEntity<JudgeResponse> {
        val updatedJudge = judgeService.updateJudge(id, request)
        return ResponseEntity.ok(updatedJudge)
    }

    @DeleteMapping("/{id}")
    fun deleteJudge(@PathVariable id: Long): ResponseEntity<Void> {
        judgeService.deleteJudge(id)
        return ResponseEntity.noContent().build()
    }
}