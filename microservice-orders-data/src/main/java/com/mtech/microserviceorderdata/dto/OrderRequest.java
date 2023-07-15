package com.mtech.microserviceorderdata.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {
    private Long id;
    private String orderNumber;
    private List<OrderLineItemDto> orderLineItemList;
}
