package com.example.petstorebackend.controller;

import com.example.petstorebackend.domain.Address;
import com.example.petstorebackend.domain.Customer;
import com.example.petstorebackend.domain.Order;
import com.example.petstorebackend.domain.Pet;
import com.example.petstorebackend.service.PetService;
import com.example.petstorebackend.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class CrudController {

    private final PetService petService;
    private final Customer customerService;
    private final Order storeService;

    @Autowired
    public CrudController(PetService petService, Customer customerService, Order storeService) {
        this.petService = petService;
        this.customerService = customerService;
        this.storeService = storeService;
    }

    public CrudController(PetService petService) {
        this.petService = petService;
        this.customerService = null;
        this.storeService = null;
    }

    public CrudController(Customer customerService) {
        this.petService = null;
        this.customerService = customerService;
        this.storeService = null;
    }

    public CrudController(Order storeService) {
        this.petService = null;
        this.customerService = null;
        this.storeService = storeService;
    }

    // Add a new pet to the store
    @PostMapping("/pet")//C
    public Mono<Result> savePet(@RequestBody Pet pet) {
        return petService.createNewPet(pet);
    }

    // Updates a pet in the store with form data
    @PostMapping("/pet/{petId}")//C
    public Mono<Result> updatePet(@RequestBody Pet pet) {
        return petService.createNewPet(pet);
    }

    // uploads an image
    @PostMapping("/pet/{petId}/uploadImage")//C
    public Mono<Result> updatePet(@RequestBody Pet pet) {
        return petService.createNewPet(pet);
    }

    // Finds Pets by status
    @GetMapping("/pet/findByStatus")//R
    public Mono<Pet> getPet(@PathVariable() String petId) {
        return petService.getPetByPetId(petId);
    }

    // Finds Pets by tags
    @GetMapping("/pet/findByTags")//R
    public Mono<Pet> getPet(@PathVariable() String petId) {
        return petService.getPetByPetId(petId);
    }

    // Find pet by ID
    @GetMapping("/get/{petId}")//R
    public Mono<Pet> getPet(@PathVariable() String petId) {
        return petService.getPetByPetId(petId);
    }

//    @PutMapping("/updatePetOrCreate")//U
//    public Mono<Result> updateOrCreatePet(@RequestBody Pet pet) {
//        return petService.updateExistingOrCreatePet(pet);
//    }

    // Deletes a pet
    @DeleteMapping("/pet/{petId}")//D
    public Mono<Result> deletePet(@PathVariable() String petId) {
        return petService.deletePetByPetId(petId);
    }

//    @PutMapping("/updatePet")
//    public Mono<Result> updatePet(@RequestBody Pet pet) {
//        return petService.updateExistingPet(pet);
//    }

//    @GetMapping("/query/{petId}")
//    public Mono<Address> queryPetAddress(@PathVariable() String petId) {
//        return petService.queryAddressByPetId(petId);
//    }

    @GetMapping("/allPetList")
    public Flux<Pet> getAllPet() {
        return petService.getPetList();
    }

    //=========================Store ==========================================

    // Returns pet inventories by status
    @GetMapping("/store/inventory")
    public Flux<Pet> getAllPet() {
        return petService.getPetList();
    }

    // Place an order for a pet
    @PostMapping("/store/order")
    public Mono<Result> updatePet(@RequestBody Pet pet) {
        return petService.createNewPet(pet);
    }

    // Find purchase order by ID
    @GetMapping("/store/order/{orderId}")
    public Flux<Pet> getAllPet() {
        return petService.getPetList();
    }

    // Delete purchase order by ID
    @DeleteMapping("/store/order/{orderId}")//D
    public Mono<Result> deletePet(@PathVariable() String petId) {
        return petService.deletePetByPetId(petId);
    }

    //=========================Customer ==========================================

    // Create user
    @PostMapping("/user")
    public Mono<Result> updatePet(@RequestBody Pet pet) {
        return petService.createNewPet(pet);
    }

    // Creates list of users with given input array
    @PostMapping("/user/createWithList")
    public Mono<Result> updatePet(@RequestBody Pet pet) {
        return petService.createNewPet(pet);
    }

    // Logs user into the system
    @GetMapping("/user/login")
    public Flux<Pet> getAllPet() {
        return petService.getPetList();
    }

    // Logs out current logged in user session
    @GetMapping("/user/logout")
    public Flux<Pet> getAllPet() {
        return petService.getPetList();
    }

    // Get user by user name
    @GetMapping("/user/{username}")
    public Flux<Pet> getAllPet() {
        return petService.getPetList();
    }

    // Update user
    @PutMapping("/user/{username}")
    public Mono<Result> updatePet(@RequestBody Pet pet) {
        return petService.updateExistingPet(pet);
    }

    // Delete user
    @DeleteMapping("/user/{username}")
    public Mono<Result> deletePet(@PathVariable() String petId) {
        return petService.deletePetByPetId(petId);
    }


    //batchGetItem

    //batchWriteItem
}
