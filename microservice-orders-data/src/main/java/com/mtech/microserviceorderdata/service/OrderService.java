package com.mtech.microserviceorderdata.service;

import com.mtech.microserviceorderdata.dto.OrderRequest;


public interface OrderService {
    void placeOrder(OrderRequest orderRequest);
}
