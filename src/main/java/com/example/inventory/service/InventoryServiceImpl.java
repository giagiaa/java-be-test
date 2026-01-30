package com.example.inventory.service;

import com.example.inventory.dto.InventoryRequest;
import com.example.inventory.exception.ResourceNotFoundException;
import com.example.inventory.model.Inventory;
import com.example.inventory.model.Item;
import com.example.inventory.repository.InventoryRepository;
import com.example.inventory.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final ItemRepository itemRepository;
    private final StockService stockService;

    @Override
    @Transactional(readOnly = true)
    public Page<Inventory> getAllInventories(Pageable pageable) {
        return inventoryRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public Inventory createInventory(InventoryRequest request) {
        Item item = itemRepository.findById(request.getItemId())
                .orElseThrow(() -> new ResourceNotFoundException("Item not found with id: " + request.getItemId()));

        if (request.getType() == Inventory.Type.W) {
             stockService.validateStock(request.getItemId(), request.getQty());
        }

        Inventory inventory = new Inventory();
        inventory.setItem(item);
        inventory.setQty(request.getQty());
        inventory.setType(request.getType());
        
        return inventoryRepository.save(inventory);
    }
}
