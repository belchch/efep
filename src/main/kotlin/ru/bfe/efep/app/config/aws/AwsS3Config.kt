package ru.bfe.efep.app.config.aws

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AwsS3Config(
    private val awsS3Properties: AwsS3Properties
) {
    @Bean
    fun amazonS3(): AmazonS3 {
        val credentials = BasicAWSCredentials(
            awsS3Properties.accessKey,
            awsS3Properties.secretKey
        )

        return AmazonS3ClientBuilder.standard()
            .withCredentials(AWSStaticCredentialsProvider(credentials))
            .withEndpointConfiguration(
                AwsClientBuilder.EndpointConfiguration(
                    "storage.yandexcloud.net",
                    "ru-central1"
                )
            )
            .build()
    }
}