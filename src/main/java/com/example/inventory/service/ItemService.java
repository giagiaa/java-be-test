package com.example.inventory.service;

import com.example.inventory.dto.ItemDto;
import com.example.inventory.dto.ItemRequest;
import com.example.inventory.model.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemService {
    Page<ItemDto> getAllItems(Pageable pageable);
    ItemDto getItem(Long id);
    Item createItem(ItemRequest request);
    Item updateItem(Long id, ItemRequest request);
    void deleteItem(Long id);
}
