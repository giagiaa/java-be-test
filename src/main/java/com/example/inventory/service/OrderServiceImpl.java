package com.example.inventory.service;

import com.example.inventory.dto.OrderRequest;
import com.example.inventory.exception.ResourceNotFoundException;
import com.example.inventory.model.Item;
import com.example.inventory.model.Order;
import com.example.inventory.repository.ItemRepository;
import com.example.inventory.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final StockService stockService;

    @Override
    @Transactional(readOnly = true)
    public Page<Order> getAllOrders(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public Order createOrder(OrderRequest request) {
        Item item = itemRepository.findById(request.getItemId())
                .orElseThrow(() -> new ResourceNotFoundException("Item not found with id: " + request.getItemId()));

        stockService.validateStock(request.getItemId(), request.getQty());

        Order order = new Order();
        order.setOrderNo(request.getOrderNo());
        order.setItem(item);
        order.setQty(request.getQty());
        order.setPrice(item.getPrice() * request.getQty()); // Snapshot total price or unit price? Image shows Item 1, Qty 2, Price 5. Item 1 Price is 5. So Order Price is Unit Price? 
        // Image Check: Item 1 Price 5. Order 01: Item 1, Qty 2, Price 5. 
        // Order 02: Item 2, Qty 3, Price 10. Item 2 Price 10.
        // It seems Order Price column holds the Unit Price of the item at the time of order.
        // Let's stick to Unit Price.
        order.setPrice(item.getPrice());

        return orderRepository.save(order);
    }
}
