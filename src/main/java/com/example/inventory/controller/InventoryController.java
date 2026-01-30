package com.example.inventory.controller;

import com.example.inventory.dto.InventoryRequest;
import com.example.inventory.model.Inventory;
import com.example.inventory.service.InventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventories")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping
    public ResponseEntity<Page<Inventory>> getAllInventories(Pageable pageable) {
        return ResponseEntity.ok(inventoryService.getAllInventories(pageable));
    }

    @PostMapping
    public ResponseEntity<Inventory> createInventory(@Valid @RequestBody InventoryRequest request) {
        return new ResponseEntity<>(inventoryService.createInventory(request), HttpStatus.CREATED);
    }
}
