package com.example.petstorebackend.repository;


import com.example.petstorebackend.domain.Order;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.async.SdkPublisher;
import software.amazon.awssdk.enhanced.dynamodb.*;
import software.amazon.awssdk.enhanced.dynamodb.model.PagePublisher;

import java.util.concurrent.CompletableFuture;

import static software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional.keyEqualTo;
@Repository
public class StoreRepository {

    private final DynamoDbAsyncTable<Order> orderDynamoDbAsyncTable;

    public StoreRepository(DynamoDbAsyncTable<Order> orderDynamoDbAsyncTable) {
        this.orderDynamoDbAsyncTable = orderDynamoDbAsyncTable;
    }

    //CREATE
    public CompletableFuture<Void> save(Order order) {
        return orderDynamoDbAsyncTable.putItem(order);
    }

    //READ
    public CompletableFuture<Order> getOrderByID(String orderId) {
        return orderDynamoDbAsyncTable.getItem(getKeyBuild(orderId));
    }

    //UPDATE
    public CompletableFuture<Order> updateOrder(Order order) {
        return orderDynamoDbAsyncTable.updateItem(order);
    }

    //DELETE
    public CompletableFuture<Order> deleteOrderById(String orderId) {
        return orderDynamoDbAsyncTable.deleteItem(getKeyBuild(orderId));
    }

    //READ_CUSTOMER_ADDRESS_ONLY
    public SdkPublisher<Order> getOrderAddress(String orderId) {
        return orderDynamoDbAsyncTable.query(r -> r.queryConditional(keyEqualTo(k -> k.partitionValue(orderId)))
                        .addAttributeToProject("OrderAddress"))
                .items();
    }

    //GET_ALL_ITEM
    public PagePublisher<Order> getAllOrder() {
        return orderDynamoDbAsyncTable.scan();
    }

    private Key getKeyBuild(String orderId) {
        return Key.builder()
                .partitionValue(orderId)
                .build();
    }
}
