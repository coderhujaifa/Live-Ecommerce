package com.livecommerce.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.livecommerce.order.domain.Settlement;
import java.util.UUID;

public interface SettlementRepository extends JpaRepository<Settlement, UUID> {}
