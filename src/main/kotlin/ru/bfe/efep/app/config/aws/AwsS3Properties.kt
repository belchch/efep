package ru.bfe.efep.app.config.aws

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "aws.s3")
data class AwsS3Properties(
    val accessKey: String,
    val secretKey: String,
    val bucketName: String,
    val endpoint: String,
    val region: String,
)