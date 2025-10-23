package com.livecommerce.order.domain;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.*;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

  @Id
  @GeneratedValue
  private UUID id;

  private String userId;
  private BigDecimal totalAmount;

  @Enumerated(EnumType.STRING)
  private OrderStatus status; // CREATED, PAID, SHIPPED, DELIVERED, CANCELLED

  private OffsetDateTime createdAt;
  private OffsetDateTime updatedAt;

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<OrderItem> items = new ArrayList<>();
}