package com.example.petstorebackend.config;

import com.example.petstorebackend.domain.Customer;
import com.example.petstorebackend.domain.Order;
import com.example.petstorebackend.domain.Pet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;

import java.net.URI;

public class DynamoDbConfig {
    private final String dynamoDbEndPointUrl;

    public DynamoDbConfig(@Value("${aws.dynamodb.endpoint}") String dynamoDbEndPointUrl) {
        this.dynamoDbEndPointUrl = dynamoDbEndPointUrl;
    }

    @Bean
    public DynamoDbAsyncClient getDynamoDbAsyncClient() {
        return DynamoDbAsyncClient.builder()
                .credentialsProvider(ProfileCredentialsProvider.create("default"))
                .endpointOverride(URI.create(dynamoDbEndPointUrl))
                .build();
    }

    @Bean
    public DynamoDbEnhancedAsyncClient getDynamoDbEnhancedAsyncClient(DynamoDbAsyncClient dynamoDbAsyncClient) {
        return DynamoDbEnhancedAsyncClient.builder()
                .dynamoDbClient(dynamoDbAsyncClient)
                .build();
    }

    @Bean
    public DynamoDbAsyncTable<Pet> getDynamoDbAsyncPet(DynamoDbEnhancedAsyncClient asyncClient) {
        return asyncClient.table(Pet.class.getSimpleName(), TableSchema.fromBean(Pet.class));
    }

    @Bean
    public DynamoDbAsyncTable<Customer> getDynamoDbAsyncCustomer(DynamoDbEnhancedAsyncClient asyncClient) {
        return asyncClient.table(Customer.class.getSimpleName(), TableSchema.fromBean(Customer.class));
    }

    @Bean
    public DynamoDbAsyncTable<Order> getDynamoDbAsyncOrder(DynamoDbEnhancedAsyncClient asyncClient) {
        return asyncClient.table(Order.class.getSimpleName(), TableSchema.fromBean(Order.class));
    }
}
