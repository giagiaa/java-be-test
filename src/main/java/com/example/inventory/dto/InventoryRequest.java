package com.example.inventory.dto;

import com.example.inventory.model.Inventory;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class InventoryRequest {
    @NotNull(message = "Item ID is mandatory")
    private Long itemId;

    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer qty;

    @NotNull(message = "Type is mandatory")
    private Inventory.Type type;
}
