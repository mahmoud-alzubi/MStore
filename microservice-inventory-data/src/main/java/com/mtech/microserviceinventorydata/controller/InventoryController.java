package com.mtech.microserviceinventorydata.controller;

import com.mtech.microserviceinventorydata.dto.InventoryRequest;
import com.mtech.microserviceinventorydata.entity.Inventory;
import com.mtech.microserviceinventorydata.service.InventoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;

@RestController
@RequestMapping("api/inventories")
public class InventoryController {
    private final InventoryServiceImpl service;

    @Autowired
    public InventoryController(InventoryServiceImpl service) {
        this.service = service;
    }


    @GetMapping("/{skuCode}")
    public ResponseEntity<?> isInStock(@PathVariable("skuCode") String skuCode) {
        boolean status = service.isInStock(skuCode);
        return ResponseEntity.ok(status);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody InventoryRequest request) {
        service.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


}
