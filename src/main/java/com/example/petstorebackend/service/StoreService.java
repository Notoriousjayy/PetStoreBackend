package com.example.petstorebackend.service;

import com.example.petstorebackend.domain.Order;
import com.example.petstorebackend.repository.StoreRepository;
import com.example.petstorebackend.util.Result;
import com.example.petstorebackend.util.Status;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.Map;
import java.util.Objects;
import org.threeten.bp.OffsetDateTime;
import java.util.function.LongSupplier;

import static com.example.petstorebackend.util.Result.FAIL;
import static com.example.petstorebackend.util.Result.SUCCESS;

@Service
public class StoreService {
    private final StoreRepository orderRepository;
    private final OffsetDateTime getEpochSecond = null;

    public StoreService(StoreRepository orderRepository) {
        this.orderRepository = orderRepository;
    }


    public Mono<Result> createNewOrder(Order order) {
        order.shipDate(getEpochSecond.now()); // change to order ship date
        return Mono.fromFuture(orderRepository.save(order))
                .thenReturn(SUCCESS)
                .onErrorReturn(FAIL);
    }

    public Mono<Order> getOrderByOrderId(String orderId) {
        return Mono.fromFuture(orderRepository.getOrderByID(orderId))
                .doOnSuccess(Objects::requireNonNull)
                .onErrorReturn(new Order());
    }


    public Mono<Result> deleteOrderByOrderId(String orderId) {
        return Mono.fromFuture(orderRepository.deleteOrderById(orderId))
                .doOnSuccess(Objects::requireNonNull)
                .thenReturn(SUCCESS)
                .onErrorReturn(FAIL);
    }

    public Flux<Order> getOrderList() {
        return Flux.from(orderRepository.getAllOrder()
                        .items())
                .onErrorReturn(new Order());
    }

    //----------------------------------------------------------------------------------------------------------------
    public Flux<Map<Status, Integer>> getStoreInventory() {
        return Flux.from(orderRepository.getAllOrder()
                        .items())
                .onErrorReturn(new Order());
    }
}
