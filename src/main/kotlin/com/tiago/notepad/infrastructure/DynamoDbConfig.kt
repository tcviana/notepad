package com.tiago.notepad.infrastructure

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.dynamodb.DynamoDbClient
import java.net.URI

@Configuration
class DynamoDbConfig {

    @Bean
    fun dynamoDbClient(): DynamoDbClient {
        return DynamoDbClient.builder()
            .endpointOverride(URI.create("http://localstack:4566"))
            .region(Region.US_EAST_1)
            .build()
    }
}