package com.livecommerce.order.api;

import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import com.livecommerce.order.domain.Order;
import com.livecommerce.order.repository.OrderRepository;

import java.time.OffsetDateTime;
import java.util.*;
import java.math.BigDecimal;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderRepository repo;

    @GetMapping
    public Map<String, Object> list() {
        return Map.of("success", true, "data", repo.findAll());
    }

    @GetMapping("/{id}")
    public Order get(@PathVariable UUID id) {
        return repo.findById(id).orElseThrow();
    }

    @PostMapping
    public Order create(@RequestBody Map<String, Object> req) {
        Order o = Order.builder()
                .amount(new BigDecimal(String.valueOf(req.getOrDefault("amount", "4999"))))
                .currency(String.valueOf(req.getOrDefault("currency", "INR")))
                .status("created")
                .createdAt(OffsetDateTime.now())
                .build();
        return repo.save(o);
    }
}
