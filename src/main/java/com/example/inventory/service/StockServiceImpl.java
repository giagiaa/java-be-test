package com.example.inventory.service;

import com.example.inventory.exception.InsufficientStockException;
import com.example.inventory.repository.InventoryRepository;
import com.example.inventory.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {

    private final InventoryRepository inventoryRepository;
    private final OrderRepository orderRepository;

    @Override
    @Transactional(readOnly = true)
    public Long calculateStock(Long itemId) {
        Long topUps = inventoryRepository.sumTopUpByItemId(itemId);
        Long withdrawals = inventoryRepository.sumWithdrawalByItemId(itemId);
        Long orders = orderRepository.sumQuantityByItemId(itemId);

        return topUps - withdrawals - orders;
    }

    @Override
    @Transactional(readOnly = true)
    public void validateStock(Long itemId, Integer requiredQty) {
        Long currentStock = calculateStock(itemId);
        if (currentStock < requiredQty) {
            throw new InsufficientStockException("Insufficient stock for item ID: " + itemId + ". Current stock: " + currentStock + ", Required: " + requiredQty);
        }
    }
}
