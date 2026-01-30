package com.example.inventory.service;

import com.example.inventory.dto.InventoryRequest;
import com.example.inventory.model.Inventory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InventoryService {
    Page<Inventory> getAllInventories(Pageable pageable);
    Inventory createInventory(InventoryRequest request);
}
