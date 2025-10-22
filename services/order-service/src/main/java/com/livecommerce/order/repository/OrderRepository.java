package com.livecommerce.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.livecommerce.order.domain.Order;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {}
