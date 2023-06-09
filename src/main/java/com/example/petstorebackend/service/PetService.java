package com.example.petstorebackend.service;


import com.example.petstorebackend.domain.Address;
import com.example.petstorebackend.domain.Pet;
import com.example.petstorebackend.domain.Tag;
import com.example.petstorebackend.repository.PetRepository;
import com.example.petstorebackend.util.Result;
import com.example.petstorebackend.util.Status;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.function.LongSupplier;

import static com.example.petstorebackend.util.Result.FAIL;
import static com.example.petstorebackend.util.Result.SUCCESS;

@Service
public class PetService {

    private final PetRepository petRepository;
    private final LongSupplier getEpochSecond = () -> Instant.now()
            .getEpochSecond();

    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    // Update this to use OffsetDateTime
    public Mono<Result> createNewPet(Pet pet) {
        pet.setCreatedTimeStamp(getEpochSecond.getAsLong());
        return Mono.fromFuture(petRepository.save(pet))
                .thenReturn(SUCCESS)
                .onErrorReturn(FAIL);
    }

    public Mono<Pet> getPetByPetId(String petId) {
        return Mono.fromFuture(petRepository.getPetByID(petId))
                .doOnSuccess(Objects::requireNonNull)
                .onErrorReturn(new Pet());
    }

    public Mono<Result> updateExistingPet(Pet pet) {
        pet.setCreatedTimeStamp(getEpochSecond.getAsLong());
        return Mono.fromFuture(
                petRepository.getPetByID(pet.getId()))
                .doOnSuccess(Objects::requireNonNull)
                .doOnNext(__ -> petRepository.updatePet(pet))
                .doOnSuccess(Objects::requireNonNull)
                .thenReturn(SUCCESS)
                .onErrorReturn(FAIL);
    }

    public Mono<Result> updateExistingOrCreatePet(Pet pet) {
        pet.setCreatedTimeStamp(getEpochSecond.getAsLong());
        return Mono.fromFuture(petRepository.updatePet(pet))
                .thenReturn(SUCCESS)
                .onErrorReturn(FAIL);
    }

    public Mono<Result> deletePetByPetId(String petId) {
        return Mono.fromFuture(petRepository.deletePetById(petId))
                .doOnSuccess(Objects::requireNonNull)
                .thenReturn(SUCCESS)
                .onErrorReturn(FAIL);
    }

    public Flux<Pet> getPetList() {
        return Flux.from(petRepository.getAllPet()
                        .items())
                .onErrorReturn(new Pet());
    }

    //----------------------------------------------------------------------------------------------------------------
    public Mono<Pet> getPetByStatus(Status status) {
        return Mono.fromFuture(petRepository.getPetByID(status))
                .doOnSuccess(Objects::requireNonNull)
                .onErrorReturn(new Pet());
    }

    public Mono<Pet> getPetByByTags(List<Tag> tags) {
        return Mono.fromFuture(petRepository.getPetByID(tags))
                .doOnSuccess(Objects::requireNonNull)
                .onErrorReturn(new Pet());
    }

    public Mono<Result> updatePet(String petId) {
        pet.setCreatedTimeStamp(getEpochSecond.getAsLong());
        return Mono.fromFuture(petRepository.updatePet(petId))
                .thenReturn(SUCCESS)
                .onErrorReturn(FAIL);
    }

    public Mono<Result> updateImage(String petId) {
        pet.setCreatedTimeStamp(getEpochSecond.getAsLong());
        return Mono.fromFuture(petRepository.updatePet(petId))
                .thenReturn(SUCCESS)
                .onErrorReturn(FAIL);
    }
}
