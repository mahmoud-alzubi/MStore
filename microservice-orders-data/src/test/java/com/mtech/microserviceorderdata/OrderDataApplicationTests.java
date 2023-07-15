package com.mtech.microserviceorderdata;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mtech.microserviceorderdata.dto.OrderLineItemDto;
import com.mtech.microserviceorderdata.dto.OrderRequest;
import com.mtech.microserviceorderdata.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc

class OrderDataApplicationTests {

    @Container
    static MySQLContainer mysql = new MySQLContainer<>(DockerImageName.parse("mysql:5.7.34"));
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private OrderService orderService;


    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.datasource.url", mysql::getJdbcUrl);
        dynamicPropertyRegistry.add("spring.datasource.username", mysql::getUsername);
        dynamicPropertyRegistry.add("spring.datasource.password", mysql::getPassword);
        dynamicPropertyRegistry.add("spring.datasource.driver-class-name", mysql::getDriverClassName);
    }

    @Test
    void shouldPlaceOrder() throws Exception {
        OrderRequest orderRequest = getTestOrderRequest();
        mockMvc.perform(MockMvcRequestBuilders.post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderRequest)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    private OrderRequest getTestOrderRequest() {
        OrderLineItemDto item1 = new OrderLineItemDto();
        item1.setSkuCode("SKU001");
        item1.setPrice(9.99);
        item1.setQuantity(2);

        OrderLineItemDto item2 = new OrderLineItemDto();
        item2.setSkuCode("SKU002");
        item2.setPrice(5.99);
        item2.setQuantity(1);

        List<OrderLineItemDto> itemList = new ArrayList<>();
        itemList.add(item1);
        itemList.add(item2);

        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setOrderLineItemList(itemList);
        return orderRequest;
    }

}
