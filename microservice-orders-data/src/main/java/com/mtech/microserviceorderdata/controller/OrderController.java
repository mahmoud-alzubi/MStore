package com.mtech.microserviceorderdata.controller;

import com.mtech.microserviceorderdata.dto.OrderRequest;
import com.mtech.microserviceorderdata.service.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderServiceImpl service;


    @Autowired
    public OrderController(OrderServiceImpl service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> placeOrder(@RequestBody OrderRequest orderRequest) {
        service.placeOrder(orderRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
