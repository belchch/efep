package ru.bfe.efep.app.inspection.structelem

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
@RequestMapping("/api/users")
class StructElemController(
    private val structElemService: StructElemService
) {

    @PostMapping
    fun createStructElem(@RequestBody request: StructElemUpdateRequest): ResponseEntity<StructElemResponse> {
        val createdStructElem = structElemService.createStructElem(request)
        return ResponseEntity(createdStructElem, HttpStatus.CREATED)
    }

    @GetMapping("/{id}")
    fun getStructElem(@PathVariable id: Long): ResponseEntity<StructElemResponse> {
        val court = structElemService.getStructElem(id)
        return ResponseEntity.ok(court)
    }

    @GetMapping
    fun getAllStructElems(): ResponseEntity<List<StructElemResponse>> {
        val courts = structElemService.getAllStructElems()
        return ResponseEntity.ok(courts)
    }

    @PutMapping("/{id}")
    fun updateStructElem(
        @PathVariable id: Long,
        @RequestBody request: StructElemUpdateRequest
    ): ResponseEntity<StructElemResponse> {
        val updatedStructElem = structElemService.updateStructElem(id, request)
        return ResponseEntity.ok(updatedStructElem)
    }

    @DeleteMapping("/{id}")
    fun deleteStructElem(@PathVariable id: Long): ResponseEntity<Void> {
        structElemService.deleteStructElem(id)
        return ResponseEntity.noContent().build()
    }
}