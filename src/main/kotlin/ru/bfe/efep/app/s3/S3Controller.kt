package ru.bfe.efep.app.s3

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/s3")
class S3Controller(
    private val s3Service: S3Service
) {
    @GetMapping("/generate-upload-url")
    fun getUploadUrl(
        @RequestParam objectKey: String,
    ): String {
        return s3Service.generateUploadUrl(objectKey)
    }

    @GetMapping("/generate-download-url")
    fun getDownloadUrl(
        @RequestParam objectKey: String
    ): String {
        return s3Service.generateDownloadUrl(objectKey)
    }
}