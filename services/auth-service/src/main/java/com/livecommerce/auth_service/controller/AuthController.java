package com.livecommerce.auth_service.controller;

import com.livecommerce.auth_service.dto.*;
import com.livecommerce.auth_service.entity.User;
import com.livecommerce.auth_service.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    public AuthController(UserService userService) { this.userService = userService; }

    @PostMapping("/register")
    public Map<String, String> register(@RequestBody RegisterRequest request) {
        return userService.register(request);
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody LoginRequest request) {
        return userService.login(request);
    }

    @PostMapping("/refresh")
    public Map<String, String> refresh(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String role = body.get("role");
        return userService.refresh(username, role);
    }

    @GetMapping("/me")
    public User me(@RequestParam String username) {
        return userService.getProfile(username);
    }

    @PutMapping("/profile")
    public Map<String, String> updateProfile(@RequestParam String username,
                                             @RequestBody UpdateProfileRequest request) {
        userService.updateProfile(username, request);
        return Map.of("message", "Profile updated successfully");
    }

    @PostMapping("/logout")
    public Map<String, String> logout() {
        return Map.of("message", "Logout successful");
    }
}
