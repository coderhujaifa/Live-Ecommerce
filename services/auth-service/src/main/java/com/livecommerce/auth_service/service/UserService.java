package com.livecommerce.auth_service.service;

import com.livecommerce.auth_service.dto.*;
import com.livecommerce.auth_service.entity.User;
import com.livecommerce.auth_service.exception.CustomException;
import com.livecommerce.auth_service.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserService(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    public Map<String, String> register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new CustomException("Email already registered");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole().toUpperCase());
        userRepository.save(user);

        String token = jwtService.generateToken(user.getUsername(), Map.of("role", user.getRole()));
        return Map.of("access_token", token);
    }

    public Map<String, String> login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new CustomException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new CustomException("Invalid credentials");
        }

        String token = jwtService.generateToken(user.getUsername(), Map.of("role", user.getRole()));
        return Map.of("access_token", token);
    }

    public Map<String, String> refresh(String username, String role) {
        String token = jwtService.generateToken(username, Map.of("role", role));
        return Map.of("access_token", token);
    }

    public User getProfile(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException("User not found"));
    }

    public void updateProfile(String username, UpdateProfileRequest request) {
        User user = getProfile(username);
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        userRepository.save(user);
    }
}
