package com.example.compliance_service.repository;

import com.example.compliance_service.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KycRepository extends JpaRepository<KycInfo, Long> {
    Optional<KycInfo> findByUserId(Long userId);
}
