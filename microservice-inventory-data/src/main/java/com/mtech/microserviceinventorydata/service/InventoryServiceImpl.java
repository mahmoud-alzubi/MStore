package com.mtech.microserviceinventorydata.service;

import com.mtech.microserviceinventorydata.dto.InventoryRequest;
import com.mtech.microserviceinventorydata.entity.Inventory;
import com.mtech.microserviceinventorydata.repository.InventoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
     * To check item stock status.
     *
     * @param skuCode
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public boolean isInStock(String skuCode) {
        try {
            log.info("isInStock({})");
            Optional<Inventory> inventory = repository.findBySkuCode(skuCode);
            return inventory.isPresent();
        } finally {
            log.info("/isInStock({})");
        }
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
