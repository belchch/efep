package ru.bfe.efep.app.s3

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest
import org.springframework.stereotype.Service
import ru.bfe.efep.app.config.aws.AwsS3Properties
import java.util.Date
import java.util.concurrent.TimeUnit

@Service
class S3Service(
    private val amazonS3: AmazonS3,
    private val awsS3Properties: AwsS3Properties
) {
    init {
        println(awsS3Properties)
    }
    fun generateUploadUrl(fileName: String, expirationTimeInMinutes: Long = 5): String {
        val expiration = Date()
        expiration.time += TimeUnit.MINUTES.toMillis(expirationTimeInMinutes)

        val generatePresignedUrlRequest = GeneratePresignedUrlRequest(
            awsS3Properties.bucketName, fileName
        ).withMethod(com.amazonaws.HttpMethod.PUT)
            .withExpiration(expiration)

        return amazonS3.generatePresignedUrl(generatePresignedUrlRequest).toString()
    }

    fun generateDownloadUrl(fileName: String, expirationTimeInMinutes: Long = 5): String {
        val expiration = Date()
        expiration.time += TimeUnit.MINUTES.toMillis(expirationTimeInMinutes)

        val generatePresignedUrlRequest = GeneratePresignedUrlRequest(
            awsS3Properties.bucketName, fileName
        ).withExpiration(expiration)

        return amazonS3.generatePresignedUrl(generatePresignedUrlRequest).toString()
    }
}