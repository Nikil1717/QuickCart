package com.quickcart.auth.service;

import com.quickcart.auth.entity.RefreshToken;
import com.quickcart.auth.entity.User;
import com.quickcart.auth.exception.BadRequestException;
import com.quickcart.auth.repository.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${jwt.refresh-token-expiration}")
    private long refreshTokenExpiration;

    public RefreshTokenService(
            RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Transactional
    public RefreshToken createRefreshToken(User user) {

        refreshTokenRepository.deleteByUser(user);

        RefreshToken token = new RefreshToken();

        token.setUser(user);
        token.setToken(UUID.randomUUID().toString());

        token.setExpiryDate(
                LocalDateTime.now()
                        .plusSeconds(refreshTokenExpiration / 1000)
        );

        return refreshTokenRepository.save(token);
    }
    
    public RefreshToken findByToken(String token) {

        return refreshTokenRepository
                .findByToken(token)
                .orElseThrow(() ->
                        new BadRequestException(
                                "Invalid Refresh Token"));
    }

    public boolean isExpired(
            RefreshToken refreshToken) {

        return refreshToken.getExpiryDate()
                .isBefore(LocalDateTime.now());
    }
}