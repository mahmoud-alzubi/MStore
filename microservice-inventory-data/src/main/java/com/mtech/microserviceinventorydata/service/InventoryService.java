package com.mtech.microserviceinventorydata.service;

import com.mtech.microserviceinventorydata.dto.InventoryRequest;
import org.springframework.stereotype.Service;

public interface InventoryService {
     boolean isInStock(String skuCode);
     void save(InventoryRequest request);
}
