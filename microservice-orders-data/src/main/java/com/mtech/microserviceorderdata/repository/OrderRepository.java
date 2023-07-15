package com.mtech.microserviceorderdata.repository;

import com.mtech.microserviceorderdata.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderRepository extends JpaRepository<Order, Long> {
}
