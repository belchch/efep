package ru.bfe.efep.app.inspection.photodoc

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/inspections/{inspectionId}/photo-docs")
class PhotoDocController(
    private val photoDocService: PhotoDocService
) {

    @PostMapping
    fun createPhotoDoc(
        @PathVariable inspectionId: Long,
        @RequestBody request: PhotoDocUpdateRequest
    ): ResponseEntity<PhotoDocResponse> {
        val createdPhotoDoc = photoDocService.createPhotoDoc(inspectionId, request)
        return ResponseEntity(createdPhotoDoc, HttpStatus.CREATED)
    }

    @GetMapping("/{id}")
    fun getPhotoDoc(
        @PathVariable inspectionId: Long,
        @PathVariable id: Long
    ): ResponseEntity<PhotoDocResponse> {
        val court = photoDocService.getPhotoDoc(
            inspectionId = inspectionId,
            id = id
        )
        return ResponseEntity.ok(court)
    }

    @GetMapping
    fun getAllPhotoDocs(@PathVariable inspectionId: Long,): ResponseEntity<List<PhotoDocResponse>> {
        val courts = photoDocService.getAllPhotoDocs(inspectionId)
        return ResponseEntity.ok(courts)
    }

    @PutMapping("/{id}")
    fun updatePhotoDoc(
        @PathVariable inspectionId: Long,
        @PathVariable id: Long,
        @RequestBody request: PhotoDocUpdateRequest
    ): ResponseEntity<PhotoDocResponse> {
        val updatedPhotoDoc = photoDocService.updatePhotoDoc(id = id, inspectionId = inspectionId, request)
        return ResponseEntity.ok(updatedPhotoDoc)
    }

    @DeleteMapping("/{id}")
    fun deletePhotoDoc(
        @PathVariable inspectionId: Long,
        @PathVariable id: Long
    ): ResponseEntity<Void> {
        photoDocService.deletePhotoDoc(id)
        return ResponseEntity.noContent().build()
    }
}