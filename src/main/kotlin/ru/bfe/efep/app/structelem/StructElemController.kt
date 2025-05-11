package ru.bfe.efep.app.structelem

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/struct-elems")
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
        val structElem = structElemService.getStructElem(id)
        return ResponseEntity.ok(structElem)
    }

    @GetMapping
    fun getAllStructElems(): ResponseEntity<List<StructElemResponse>> {
        val structElems = structElemService.getAllStructElems()
        return ResponseEntity.ok(structElems)
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