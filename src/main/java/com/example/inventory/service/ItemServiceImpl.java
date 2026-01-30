package com.example.inventory.service;

import com.example.inventory.dto.ItemDto;
import com.example.inventory.dto.ItemRequest;
import com.example.inventory.exception.ResourceNotFoundException;
import com.example.inventory.model.Item;
import com.example.inventory.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final StockService stockService;

    @Override
    @Transactional(readOnly = true)
    public Page<ItemDto> getAllItems(Pageable pageable) {
        return itemRepository.findAll(pageable)
                .map(this::mapToDto);
    }

    @Override
    @Transactional(readOnly = true)
    public ItemDto getItem(Long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found with id: " + id));
        return mapToDto(item);
    }

    @Override
    @Transactional
    public Item createItem(ItemRequest request) {
        Item item = new Item();
        item.setName(request.getName());
        item.setPrice(request.getPrice());
        return itemRepository.save(item);
    }

    @Override
    @Transactional
    public Item updateItem(Long id, ItemRequest request) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found with id: " + id));
        item.setName(request.getName());
        item.setPrice(request.getPrice());
        return itemRepository.save(item);
    }

    @Override
    @Transactional
    public void deleteItem(Long id) {
        if (!itemRepository.existsById(id)) {
            throw new ResourceNotFoundException("Item not found with id: " + id);
        }
        itemRepository.deleteById(id);
    }

    private ItemDto mapToDto(Item item) {
        Long stock = stockService.calculateStock(item.getId());
        return ItemDto.builder()
                .id(item.getId())
                .name(item.getName())
                .price(item.getPrice())
                .stock(stock)
                .build();
    }
}
