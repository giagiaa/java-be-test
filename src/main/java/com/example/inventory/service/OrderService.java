package com.example.inventory.service;

import com.example.inventory.dto.OrderRequest;
import com.example.inventory.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    Page<Order> getAllOrders(Pageable pageable);
    Order createOrder(OrderRequest request);
}
