package com.mtech.microserviceorderdata.dto;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class OrderLineItemDto {
    private Long id;
    private String skuCode;
    private Double price;
    private Integer quantity;
}
