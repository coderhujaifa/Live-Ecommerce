package com.example.compliance_service.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KycVerifyRequest {

    private boolean verified;   // true = approve, false = reject
    private String remarks;
}
