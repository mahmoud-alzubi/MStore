package com.mtech.microserviceorderdata.service;

import com.mtech.microserviceorderdata.dto.OrderLineItemDto;
import com.mtech.microserviceorderdata.dto.OrderRequest;
import com.mtech.microserviceorderdata.entity.Order;
import com.mtech.microserviceorderdata.entity.OrderLineItem;
import com.mtech.microserviceorderdata.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final OrderRepository repository;

    @Autowired
    public OrderServiceImpl(OrderRepository repository) {
        this.repository = repository;
    }


    ///////////////////////////////////////////////////

    /**
     * To place a new order.
     *
     * @param orderRequest
     */
    @Override
    @Transactional
    public void placeOrder(OrderRequest orderRequest) {
        try {
            log.info("placeOrder({})", orderRequest);
            List<OrderLineItem> orderLineItemList = orderRequest.getOrderLineItemList().stream().map(
                    item -> mapOrderLineItemsToDto(item)).toList();

            Order order = new Order();
            order.setOrderNumber(UUID.randomUUID().toString());
            order.setOrderLineItemList(orderLineItemList);

            repository.save(order);
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
