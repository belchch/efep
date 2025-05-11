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
        val photoDoc = photoDocService.getPhotoDoc(
            inspectionId = inspectionId,
            id = id
        )
        return ResponseEntity.ok(photoDoc)
    }

    @GetMapping
    fun searchPhotoDocs(
        @PathVariable inspectionId: Long,
        @RequestParam spotId: List<Long>?,
        @RequestParam structElemId: List<Long>?,
        @RequestParam materialId: List<Long>?,
        @RequestParam type: List<PhotoDocType>?,
        @RequestParam spotIdIsNull: Boolean?,
        @RequestParam structElemIdIsNull: Boolean?,
        @RequestParam materialIdIsNull: Boolean?,
        @RequestParam typeIsNull: Boolean?
    ): ResponseEntity<List<PhotoDocResponse>> {
        fun <T> appendNull(list: List<T>?, hasNull: Boolean?) =
            if (hasNull == true) listOf(null) + (list ?: emptyList()) else list


        val photoDocs = photoDocService.searchPhotoDocs(
            inspectionId = inspectionId,
            spotIds = appendNull(spotId, spotIdIsNull),
            structElemIds = appendNull(structElemId, structElemIdIsNull),
            materialIds = appendNull(materialId, materialIdIsNull),
            types = appendNull(type, typeIsNull)
        )

        return ResponseEntity.ok(photoDocs)
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