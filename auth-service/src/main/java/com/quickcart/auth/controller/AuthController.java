package com.quickcart.auth.controller;

import com.quickcart.auth.dto.LoginRequest;
import com.quickcart.auth.dto.LoginResponse;
import com.quickcart.auth.dto.RefreshTokenRequest;
import com.quickcart.auth.dto.RegisterRequest;
import com.quickcart.auth.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(
            AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(
            @Valid
            @RequestBody RegisterRequest request) {

        authService.register(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("User Registered Successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid
            @RequestBody LoginRequest request) {

        return ResponseEntity.ok(
                authService.login(request));
    }
    
    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refreshToken(
            @Valid
            @RequestBody RefreshTokenRequest request) {

        return ResponseEntity.ok(
                authService.refreshToken(
                        request.getRefreshToken()
                )
        );
    }
}