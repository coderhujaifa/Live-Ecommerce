//
//package com.livecommerce.product.config;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
//import org.springframework.security.oauth2.jwt.JwtDecoder;
//import javax.crypto.spec.SecretKeySpec;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//public class SecurityConfig {
//  private static final String DEFAULT_SECRET_BASE64 = "MDEyMzQ1Njc4OUFCQ0RFRjAxMjM0NTY3ODlBQkNERUYwMTI="; // demo only
//
//  @Bean
//  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//    http.csrf(csrf -> csrf.disable())
//        .authorizeHttpRequests(auth -> auth
//          .requestMatchers(HttpMethod.GET, "/**").permitAll()
//          .anyRequest().authenticated())
//        .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));
//    return http.build();
//  }
//
//  @Bean
//  JwtDecoder jwtDecoder() {
//    String secret = System.getenv().getOrDefault("JWT_SECRET_BASE64", DEFAULT_SECRET_BASE64);
//    byte[] key = java.util.Base64.getDecoder().decode(secret);
//    var secretKey = new SecretKeySpec(key, "HmacSHA256");
//    return NimbusJwtDecoder.withSecretKey(secretKey).build();
//  }
//}
