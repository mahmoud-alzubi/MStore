package com.mtech.microserviceorderdata.service;

import com.mtech.commons.dto.InventoryResponse;
import com.mtech.commons.events.OrderPlacedEvent;
import com.mtech.microserviceorderdata.dto.OrderLineItemDto;
import com.mtech.microserviceorderdata.dto.OrderRequest;
import com.mtech.microserviceorderdata.entity.Order;
import com.mtech.microserviceorderdata.entity.OrderLineItem;
import com.mtech.microserviceorderdata.exception.ProductNotFoundException;
import com.mtech.microserviceorderdata.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final OrderRepository repository;
    private final WebClient.Builder clientBuilder;
    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

    @Autowired
    public OrderServiceImpl(OrderRepository repository, WebClient.Builder clientBuilder, KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate) {
        this.repository = repository;
        this.clientBuilder = clientBuilder;
        this.kafkaTemplate = kafkaTemplate;
    }


    ///////////////////////////////////////////////////

    /**
     * To place a new order.
     *
     * @param orderRequest
     * @return
     */
    @Override
    @Transactional
    public String placeOrder(OrderRequest orderRequest) {
        try {
            log.info("placeOrder({})", orderRequest);


            List<String> listOfCodes = orderRequest.getOrderLineItemList().stream().map(orderLineItem -> {
                return orderLineItem.getSkuCode();
            }).toList();

            InventoryResponse[] inventoryResponseArray = clientBuilder.build().get()
                    .uri("http://microservice-inventory-data/api/inventories",
                            uriBuilder -> uriBuilder.queryParam("skuCode", listOfCodes).build())
                    .retrieve()
                    .bodyToMono(InventoryResponse[].class)
                    .block();

            for (InventoryResponse invItemResponse : inventoryResponseArray) {
                if (!invItemResponse.isInStock()) {
                    throw new ProductNotFoundException("Product is not in stock currently, please try another time.");
                }
            }

            List<OrderLineItem> orderLineItemList = orderRequest.getOrderLineItemList().stream().map(
                    item -> mapOrderLineItemsToDto(item)).toList();

            String orderNumber = UUID.randomUUID().toString();
            kafkaTemplate.send("notificationsTopic",
                    OrderPlacedEvent.builder().orderNumber(orderNumber).build());

            Order order = new Order();
            order.setOrderNumber(orderNumber);
            order.setOrderLineItemList(orderLineItemList);
            repository.save(order);

            return "order placed successfully!";
        } finally {
            log.info("/placeOrder({})", orderRequest);
        }

    }

    ///////////////////////////////////////////////////

    /**
     * To map order line items to data transfer objects.
     *
     * @param item
     * @return
     */
    protected OrderLineItem mapOrderLineItemsToDto(OrderLineItemDto item) {
        log.debug("mapOrderLineItemsToDto({})", item);
        return OrderLineItem.builder()
                .price(item.getPrice())
                .skuCode(item.getSkuCode())
                .quantity(item.getQuantity())
                .build();
    }
}
