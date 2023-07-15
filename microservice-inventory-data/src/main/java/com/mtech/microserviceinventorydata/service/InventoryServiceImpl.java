package com.mtech.microserviceinventorydata.service;

import com.mtech.commons.dto.InventoryResponse;
import com.mtech.commons.exception.ProductNotFoundException;
import com.mtech.microserviceinventorydata.dto.InventoryRequest;
import com.mtech.microserviceinventorydata.entity.Inventory;
import com.mtech.microserviceinventorydata.repository.InventoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository repository;

    @Autowired
    public InventoryServiceImpl(InventoryRepository repository) {
        this.repository = repository;
    }

    //////////////////////////////////////////

    /**
     * To check items stock status.
     *
     * @param skuCodes
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<InventoryResponse> isInStock(List<String> skuCodes) {
        try {
            log.info("isInStock({})");
            List<Inventory> inventoryList = repository.findBySkuCodeIn(skuCodes);
            if (inventoryList.isEmpty()) {
                throw new ProductNotFoundException("");
            }

            List<InventoryResponse> itemsInStockList = inventoryList.stream()
                    .map(inventoryItem -> mapInventoryItems(inventoryItem)).toList();

            return itemsInStockList;
        } finally {
            log.info("/isInStock({})");
        }
    }

    private InventoryResponse mapInventoryItems(Inventory inventoryItem) {
        return InventoryResponse.builder()
                .skuCode(inventoryItem.getSkuCode())
                .inStock(inventoryItem.getQuantity() > 0).build();
    }


    //////////////////////////////////////////

    /**
     * To add new record in inventory.
     *
     * @param request
     */
    @Override
    @Transactional
    public void save(InventoryRequest request) {
        try {
            log.info("save({})", request);
            Inventory inventory = new Inventory();
            inventory.setQuantity(request.getQuantity());
            inventory.setSkuCode(request.getSkuCode());
            repository.save(inventory);
        } finally {
            log.info("/save({})", request);
        }

    }
}
