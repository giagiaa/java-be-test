package com.example.inventory.repository;

import com.example.inventory.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    List<Order> findByItemId(Long itemId);

    @Query("SELECT COALESCE(SUM(o.qty), 0) FROM Order o WHERE o.item.id = :itemId")
    Long sumQuantityByItemId(@Param("itemId") Long itemId);
}
