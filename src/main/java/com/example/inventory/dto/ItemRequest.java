package com.example.inventory.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ItemRequest {
    @NotBlank(message = "Name is mandatory")
    private String name;

    @Min(value = 0, message = "Price must be positive")
    private Double price;
}
