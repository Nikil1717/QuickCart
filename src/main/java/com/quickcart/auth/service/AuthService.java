package com.quickcart.auth.service;

import com.quickcart.auth.dto.LoginRequest;
import com.quickcart.auth.dto.LoginResponse;
import com.quickcart.auth.dto.RegisterRequest;
import com.quickcart.auth.entity.RefreshToken;
import com.quickcart.auth.entity.Role;
import com.quickcart.auth.entity.User;
import com.quickcart.auth.exception.BadRequestException;
import com.quickcart.auth.repository.UserRepository;
import com.quickcart.auth.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final AuthenticationManager authenticationManager;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            RefreshTokenService refreshTokenService,
            AuthenticationManager authenticationManager) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
        this.authenticationManager = authenticationManager;
    }

    public void register(RegisterRequest request) {

        if (userRepository.existsByEmail(
                request.getEmail())) {

            throw new BadRequestException(
                    "Email already exists");
        }
        
        if (request.getRole() == Role.ADMIN) {
            throw new BadRequestException(
                    "ADMIN registration is not allowed");
        }

        User user = new User();

        user.setEmail(request.getEmail());
        user.setPassword(
                passwordEncoder.encode(
                        request.getPassword()));

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setRole(request.getRole());

        userRepository.save(user);
    }

    public LoginResponse login(
            LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow();

        String accessToken =
                jwtService.generateToken(user);

        RefreshToken refreshToken =
                refreshTokenService
                        .createRefreshToken(user);

        return new LoginResponse(
                accessToken,
                refreshToken.getToken(),
                "Bearer"
        );
    }
    
    public LoginResponse refreshToken(
            String token) {

        RefreshToken refreshToken =
                refreshTokenService.findByToken(token);

        if (refreshTokenService.isExpired(
                refreshToken)) {

            throw new BadRequestException(
                    "Refresh Token Expired");
        }

        User user = refreshToken.getUser();

        String accessToken =
                jwtService.generateToken(user);

        return new LoginResponse(
                accessToken,
                refreshToken.getToken(),
                "Bearer"
        );
    }
}