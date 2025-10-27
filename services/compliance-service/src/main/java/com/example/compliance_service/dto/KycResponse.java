package com.example.compliance_service.dto;

import com.example.compliance_service.enums.KycStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KycResponse {

    private Long userId;
    private String fullName;
    private String documentType;
    private KycStatus status;
    private String remarks;
}
