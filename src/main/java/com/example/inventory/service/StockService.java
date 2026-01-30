package com.example.inventory.service;

public interface StockService {
    Long calculateStock(Long itemId);
    void validateStock(Long itemId, Integer requiredQty);
}
