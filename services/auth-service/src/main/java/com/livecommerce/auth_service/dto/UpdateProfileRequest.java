package com.livecommerce.auth_service.dto;

import lombok.Data;

@Data
public class UpdateProfileRequest {
    private String username;
    private String email;
}
