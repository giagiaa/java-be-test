package com.example.inventory.repository;

import com.example.inventory.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    List<Inventory> findByItemId(Long itemId);

    @Query("SELECT COALESCE(SUM(i.qty), 0) FROM Inventory i WHERE i.item.id = :itemId AND i.type = 'T'")
    Long sumTopUpByItemId(@Param("itemId") Long itemId);

    @Query("SELECT COALESCE(SUM(i.qty), 0) FROM Inventory i WHERE i.item.id = :itemId AND i.type = 'W'")
    Long sumWithdrawalByItemId(@Param("itemId") Long itemId);
}
