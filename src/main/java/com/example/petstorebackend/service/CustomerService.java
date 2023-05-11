package com.example.petstorebackend.service;

import com.example.petstorebackend.domain.Address;
import com.example.petstorebackend.domain.Customer;
import com.example.petstorebackend.repository.CustomerRepository;
import com.example.petstorebackend.util.Result;
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
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final LongSupplier getEpochSecond = () -> Instant.now()
            .getEpochSecond();

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    // Update this to use OffsetDateTime
    public Mono<Result> createNewCustomer(Customer customer) {
        customer.setCreatedTimeStamp(getEpochSecond.getAsLong());
        return Mono.fromFuture(customerRepository.save(customer))
                .thenReturn(SUCCESS)
                .onErrorReturn(FAIL);
    }

    public Mono<Customer> getCustomerByCustomerId(String customerId) {
        return Mono.fromFuture(customerRepository.getCustomerByID(customerId))
                .doOnSuccess(Objects::requireNonNull)
                .onErrorReturn(new Customer());
    }

    public Mono<List<Address>> queryAddressByCustomerId(String customerId) {
        return Mono.from(customerRepository.getCustomerAddress(customerId))
                .map(Customer::getAddress)
                .doOnSuccess(Objects::requireNonNull)
                .onErrorReturn(new Address());
    }

    public Mono<Result> updateExistingCustomer(Customer customer) {
        customer.setCreatedTimeStamp(getEpochSecond.getAsLong());
        return Mono.fromFuture(customerRepository.getCustomerByID(customer.getId()))
                .doOnSuccess(Objects::requireNonNull)
                .doOnNext(__ -> customerRepository.updateCustomer(customer))
                .doOnSuccess(Objects::requireNonNull)
                .thenReturn(SUCCESS)
                .onErrorReturn(FAIL);
    }

    public Mono<Result> updateExistingOrCreateCustomer(Customer customer) {
        customer.setCreatedTimeStamp(getEpochSecond.getAsLong());
        return Mono.fromFuture(customerRepository.updateCustomer(customer))
                .thenReturn(SUCCESS)
                .onErrorReturn(FAIL);
    }

    public Mono<Result> deleteCustomerByCustomerId(String customerId) {
        return Mono.fromFuture(customerRepository.deleteCustomerById(customerId))
                .doOnSuccess(Objects::requireNonNull)
                .thenReturn(SUCCESS)
                .onErrorReturn(FAIL);
    }

    public Flux<Customer> getCustomerList() {
        return Flux.from(customerRepository.getAllCustomer()
                        .items())
                .onErrorReturn(new Customer());
    }
}
