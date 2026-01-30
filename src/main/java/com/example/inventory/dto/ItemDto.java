package com.example.inventory.dto;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class ItemDto {
    private Long id;
    private String name;
    private Double price;
    private Long stock;
}
