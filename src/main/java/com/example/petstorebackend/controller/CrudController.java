package com.example.petstorebackend.controller;

import com.example.petstorebackend.domain.*;
import com.example.petstorebackend.service.CustomerService;
import com.example.petstorebackend.service.PetService;
import com.example.petstorebackend.service.StoreService;
import com.example.petstorebackend.util.Result;
import com.example.petstorebackend.util.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

public class CrudController {

    private final PetService petService;
    private final CustomerService customerService;
    private final StoreService storeService;

    @Autowired
    public CrudController(PetService petService, CustomerService customerService, StoreService storeService) {
        this.petService = petService;
        this.customerService = customerService;
        this.storeService = storeService;
    }

    public CrudController(PetService petService) {
        this.petService = petService;
        this.customerService = null;
        this.storeService = null;
    }

    public CrudController(CustomerService customerService) {
        this.petService = null;
        this.customerService = customerService;
        this.storeService = null;
    }

    public CrudController(StoreService storeService) {
        this.petService = null;
        this.customerService = null;
        this.storeService = storeService;
    }

    // Update an existing pet
    @PutMapping(path = "/pet", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Result> updatePet(@RequestBody Pet pet) {
        return petService.updateExistingPet(pet);
    }

    // Add a new pet to the store
    @PostMapping(path ="/pet", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Result> createPet(@RequestBody Pet pet) {
        return petService.createNewPet(pet);
    }

    // Finds Pets by status
    @GetMapping(path = "/pet/findByStatus", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Pet> getPetByStatus(@RequestBody Status status) {
        return petService.getPetByStatus(status);
    }

    // Finds Pets by tags
    @GetMapping(path ="/pet/findByTags", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Pet> getPetByTags(@PathVariable() List<Tag> tags) {
        return petService.getPetByByTags(tags);
    }


    // Updates a pet in the store with form data
    @PostMapping(path = "/pet/{petId}", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Result> updatePetById(@PathVariable("petId") String petId) {
        return petService.updatePet(petId);
    }

    // Find pet by ID
    @GetMapping(path ="/get/{petId}", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Pet> getPetById(@PathVariable("petId") String petId) {
        return petService.getPetByPetId(petId);
    }

    // Deletes a pet
    @DeleteMapping(path ="/pet/{petId}")
    public Mono<Result> deletePet(@PathVariable("petId") String petId) {
        return petService.deletePetByPetId(petId);
    }
    // uploads an image
    @PostMapping(path ="/pet/{petId}/uploadImage", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Result> updateImage(@PathVariable("petId") String petId) {
        return petService.updateImage(petId);
    }



    @GetMapping(path ="/allPetList", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Flux<Pet> getAllPets() {
        return petService.getPetList();
    }

    //=========================Store ==========================================

    // Returns pet inventories by status
    @GetMapping(path ="/store/inventory", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Flux<Map<Status, Integer>> getStoreInventory() {
        return storeService.getStoreInventory();
    }

    // Place an order for a pet
    @PostMapping(path ="/store/order", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Result> orderPet(@RequestBody Order order) {
        return storeService.createNewOrder(order);
    }

    // Find purchase order by ID
    @GetMapping(path ="/store/order/{orderId}", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Order> getOrderById(@PathVariable("orderId") String orderId) {
        return storeService.getOrderByOrderId(orderId);
    }

    // Delete purchase order by ID
    @DeleteMapping(path ="/store/order/{orderId}")
    public Mono<Result> deleteOrder(@PathVariable("orderId") String petId) {
        return storeService.deleteOrderByOrderId(petId);
    }

    //=========================Customer ==========================================

    // Create user
    @PostMapping(path ="/user", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Result> createUser(@RequestBody Customer customer) {
        return customerService.createNewCustomer(customer);
    }

    // Creates list of users with given input array
    @PostMapping(path ="/user/createWithList", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Flux<Customer> createUserList(@RequestBody List<Customer> users) {
        return customerService.createUserList(users);
    }

    // Logs user into the system
    @GetMapping(path ="/user/login", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Result> login(String username, String password) {
        return customerService.login(username, password);
    }

    // Logs out current logged-in user session
    @GetMapping(path ="/user/logout", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Result> logout() {
        return customerService.logout();
    }

    // Get user by username
    @GetMapping(path ="/user/{username}", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Customer> getUserByUsername(@PathVariable("username") String username) {
        return customerService.getUserByUsername(username);
    }

    // Update user
    @PutMapping(path ="/user/{username}", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Result> updateUserByUsername(@PathVariable("username") String username) {
        return customerService.updateUserByUsername(username);
    }

    // Delete user
    @DeleteMapping(path ="/user/{username}")
    public Mono<Result> deleteUserByUsername(@PathVariable("username") String username) {
        return customerService.deleteUserByUsername(username);
    }


    //batchGetItem

    //batchWriteItem
}
