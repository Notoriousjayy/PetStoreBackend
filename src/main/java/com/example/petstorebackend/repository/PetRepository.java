package com.example.petstorebackend.repository;

import com.example.petstorebackend.domain.Pet;
import software.amazon.awssdk.core.async.SdkPublisher;
import software.amazon.awssdk.enhanced.dynamodb.*;
import software.amazon.awssdk.enhanced.dynamodb.model.PagePublisher;

import java.util.concurrent.CompletableFuture;

import static software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional.keyEqualTo;

public class PetRepository {

    private final DynamoDbAsyncTable<Pet> petDynamoDbAsyncTable;

    public PetRepository(DynamoDbAsyncTable<Pet> petDynamoDbAsyncTable) {
        this.petDynamoDbAsyncTable = petDynamoDbAsyncTable;
    }

    //CREATE
    public CompletableFuture<Void> save(Pet pet) {
        return petDynamoDbAsyncTable.putItem(pet);
    }

    //READ
    public CompletableFuture<Pet> getPetByID(String petId) {
        return petDynamoDbAsyncTable.getItem(getKeyBuild(petId));
    }

    //UPDATE
    public CompletableFuture<Pet> updatePet(Pet pet) {
        return petDynamoDbAsyncTable.updateItem(pet);
    }

    //DELETE
    public CompletableFuture<Pet> deletePetById(String petId) {
        return petDynamoDbAsyncTable.deleteItem(getKeyBuild(petId));
    }

    //READ_CUSTOMER_ADDRESS_ONLY
    public SdkPublisher<Pet> getPetAddress(String petId) {
        return petDynamoDbAsyncTable.query(r -> r.queryConditional(keyEqualTo(k -> k.partitionValue(petId)))
                        .addAttributeToProject("PetAddress"))
                .items();
    }

    //GET_ALL_ITEM
    public PagePublisher<Pet> getAllPet() {
        return petDynamoDbAsyncTable.scan();
    }

    private Key getKeyBuild(String petId) {
        return Key.builder()
                .partitionValue(petId)
                .build();
    }
}
