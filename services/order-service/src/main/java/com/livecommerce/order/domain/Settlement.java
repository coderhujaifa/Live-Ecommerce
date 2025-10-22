package com.livecommerce.order.domain;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name="settlements")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Settlement {
  @Id @GeneratedValue
  private UUID id;
  private String sellerId;
  private BigDecimal amount;
  private String currency;
  private String status; // PENDING, PAID
  private OffsetDateTime createdAt;
}
