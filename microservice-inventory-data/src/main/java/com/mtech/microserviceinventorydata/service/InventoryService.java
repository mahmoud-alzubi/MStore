package com.mtech.microserviceinventorydata.service;

import com.mtech.commons.dto.InventoryResponse;
import com.mtech.microserviceinventorydata.dto.InventoryRequest;

import java.util.List;

public interface InventoryService {
    List<InventoryResponse> isInStock(List<String> skuCodes);

    void save(InventoryRequest request);
}
