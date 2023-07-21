package com.mtech.microserviceorderdata.service;

import com.mtech.microserviceorderdata.dto.OrderRequest;


public interface OrderService {
    String placeOrder(OrderRequest orderRequest);
}
