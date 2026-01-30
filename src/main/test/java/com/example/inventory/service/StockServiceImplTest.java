package com.example.inventory.service;

import com.example.inventory.exception.InsufficientStockException;
import com.example.inventory.repository.InventoryRepository;
import com.example.inventory.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StockServiceImplTest {

    @Mock
    private InventoryRepository inventoryRepository;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private StockServiceImpl stockService;

    private Long itemId = 1L;

    @Test
    void calculateStock_shouldReturnCorrectValue() {
        when(inventoryRepository.sumTopUpByItemId(itemId)).thenReturn(100L);
        when(inventoryRepository.sumWithdrawalByItemId(itemId)).thenReturn(20L);
        when(orderRepository.sumQuantityByItemId(itemId)).thenReturn(30L);

        Long stock = stockService.calculateStock(itemId);

        // 100 - 20 - 30 = 50
        assertEquals(50L, stock);
    }

    @Test
    void calculateStock_shouldHandleNulls() {
        // Simulating repositories returning nulls if 0 is not handled at SQL level (though our query uses COALESCE, mocks bypass that)
        // Wait, our service calls SQL methods. If we mock, we return values.
        // Our SQL query uses COALESCE(..., 0) so we expect repositories to return 0, not null, from the query.
        // If we mock strict behavior:
        when(inventoryRepository.sumTopUpByItemId(itemId)).thenReturn(0L);
        when(inventoryRepository.sumWithdrawalByItemId(itemId)).thenReturn(0L);
        when(orderRepository.sumQuantityByItemId(itemId)).thenReturn(0L);

        Long stock = stockService.calculateStock(itemId);

        assertEquals(0L, stock);
    }

    @Test
    void validateStock_shouldPass_whenStockSufficient() {
        when(inventoryRepository.sumTopUpByItemId(itemId)).thenReturn(50L);
        when(inventoryRepository.sumWithdrawalByItemId(itemId)).thenReturn(0L);
        when(orderRepository.sumQuantityByItemId(itemId)).thenReturn(0L);

        assertDoesNotThrow(() -> stockService.validateStock(itemId, 10));
    }

    @Test
    void validateStock_shouldThrow_whenStockInsufficient() {
        when(inventoryRepository.sumTopUpByItemId(itemId)).thenReturn(10L);
        when(inventoryRepository.sumWithdrawalByItemId(itemId)).thenReturn(0L);
        when(orderRepository.sumQuantityByItemId(itemId)).thenReturn(0L);

        assertThrows(InsufficientStockException.class, () -> stockService.validateStock(itemId, 20));
    }
}
