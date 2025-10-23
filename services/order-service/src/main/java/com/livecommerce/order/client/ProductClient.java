package com.livecommerce.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;
import java.math.BigDecimal;
import java.util.Map;

@FeignClient(name = "product-service", url = "http://localhost:8082/api/products")
public interface ProductClient {

    @GetMapping("/{id}")
    Map<String, Object> getProductById(@PathVariable UUID id);

    @PutMapping("/{id}/reduce-stock")
    void reduceStock(@PathVariable UUID id, @RequestParam int quantity);
}