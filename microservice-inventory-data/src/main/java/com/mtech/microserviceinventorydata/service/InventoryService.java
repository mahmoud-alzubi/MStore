package com.mtech.microserviceinventorydata.service;

import com.mtech.microserviceinventorydata.dto.InventoryRequest;
import com.mtech.microserviceinventorydata.dto.InventoryResponse;
import org.springframework.stereotype.Service;

import java.util.List;

public interface InventoryService {
    List<InventoryResponse> isInStock(List<String> skuCodes);

    void save(InventoryRequest request);
}
