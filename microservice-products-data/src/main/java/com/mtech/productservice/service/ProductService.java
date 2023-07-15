package com.mtech.productservice.service;

import com.mtech.productservice.dto.ProductRequest;
import com.mtech.productservice.dto.ProductResponse;
import com.mtech.productservice.model.Product;
import com.mtech.productservice.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class ProductService {

    private final ProductRepository repository;

    @Autowired
    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    ////////////////////////////////////////////////////////

    /**
     * To retrieve all products records.
     *
     * @return
     */
    @Transactional(readOnly = true)
    public List<ProductResponse> findAllProducts() {
        try {
            log.info("findAllProducts()");
            List<Product> productsList = repository.findAll();
            return productsList.stream().map(product -> mapToProductResponse(product)).toList();
        } finally {
            log.info("/findAllProducts()");
        }
    }

    ////////////////////////////////////////////////////////

    /**
     * @param product
     * @return
     */
    protected ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice()).build();
    }


    ////////////////////////////////////////////////////////

    /**
     * To add new product record.
     *
     * @param productRequest
     */
    @Transactional
    public void createProduct(ProductRequest productRequest) {
        try {
            log.info("createProduct({})", productRequest);
            Product product = Product.builder()
                    .name(productRequest.getName())
                    .description(productRequest.getDescription())
                    .price(productRequest.getPrice())
                    .build();

            repository.save(product);
        } finally {
            log.info("/createProduct({})", productRequest);
        }

    }
}

