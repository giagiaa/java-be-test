package com.example.inventory.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderRequest {
    @NotBlank(message = "Order No is mandatory")
    private String orderNo;

    @NotNull(message = "Item ID is mandatory")
    private Long itemId;

    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer qty;
}
