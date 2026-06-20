package com.quickcart.auth.controller;

import com.quickcart.auth.dto.UpdateUserRequest;
import com.quickcart.auth.dto.UserResponse;
import com.quickcart.auth.service.UserService;
import jakarta.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(
            UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public UserResponse getCurrentUser(
            Authentication authentication) {

        return userService.getCurrentUser(
                authentication.getName());
    }

    @PutMapping("/me")
    public UserResponse updateCurrentUser(
            Authentication authentication,
            @Valid @RequestBody
            UpdateUserRequest request) {

        return userService.updateCurrentUser(
                authentication.getName(),
                request);
    }
    
  

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/admin")
    public String adminEndpoint() {
        return "Welcome Admin";
    }
}