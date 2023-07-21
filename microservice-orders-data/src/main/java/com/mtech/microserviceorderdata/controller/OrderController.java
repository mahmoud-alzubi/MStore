package com.mtech.microserviceorderdata.controller;

import com.mtech.microserviceorderdata.dto.OrderRequest;
import com.mtech.microserviceorderdata.service.OrderServiceImpl;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderServiceImpl service;


    @Autowired
    public OrderController(OrderServiceImpl service) {
        this.service = service;
    }

    @PostMapping
    @CircuitBreaker(name = "inventory", fallbackMethod = "fallBackMethod")
    @TimeLimiter(name = "inventory")
    @Retry(name = "inventory")
    public CompletionStage<ResponseEntity<String>> placeOrder(@RequestBody OrderRequest orderRequest) {
        CompletableFuture<String> response = CompletableFuture.supplyAsync(() -> service.placeOrder(orderRequest));
        return response.thenApply(result -> ResponseEntity.status(HttpStatus.CREATED).body(result));
    }

    // Fallback method for the CircuitBreaker
    public CompletionStage<ResponseEntity<String>> fallBackMethod(Exception exception) {
        return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Fallback response: " + exception.getMessage()));
    }

}
