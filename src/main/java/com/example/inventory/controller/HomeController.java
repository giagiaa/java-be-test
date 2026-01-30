package com.example.inventory.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HomeController {

    @GetMapping("/")
    public Map<String, String> home() {
        Map<String, String> info = new HashMap<>();
        info.put("message", "Welcome to the Inventory API");
        info.put("items_url", "/api/items");
        info.put("inventories_url", "/api/inventories");
        info.put("orders_url", "/api/orders");
        info.put("h2_console", "/h2-console");
        return info;
    }
}
