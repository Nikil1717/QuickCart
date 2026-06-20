package com.quickcart.auth.service;

import com.quickcart.auth.dto.UpdateUserRequest;
import com.quickcart.auth.dto.UserResponse;
import com.quickcart.auth.entity.User;
import com.quickcart.auth.exception.ResourceNotFoundException;
import com.quickcart.auth.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(
            UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponse getCurrentUser(
            String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"));

        return mapToResponse(user);
    }

    public UserResponse updateCurrentUser(
            String email,
            UpdateUserRequest request) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"));

        user.setFirstName(
                request.getFirstName());

        user.setLastName(
                request.getLastName());

        user.setPhoneNumber(
                request.getPhoneNumber());

        userRepository.save(user);

        return mapToResponse(user);
    }

    private UserResponse mapToResponse(
            User user) {

        UserResponse response =
                new UserResponse();

        response.setId(user.getId());
        response.setEmail(user.getEmail());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setPhoneNumber(user.getPhoneNumber());
        response.setRole(user.getRole());

        return response;
    }
}