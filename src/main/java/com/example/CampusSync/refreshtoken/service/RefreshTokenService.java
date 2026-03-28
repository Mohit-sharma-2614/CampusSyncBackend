package com.example.CampusSync.refreshtoken.service;

import com.example.CampusSync.refreshtoken.model.RefreshTokens;
import com.example.CampusSync.refreshtoken.repository.RefreshTokenRepository;
import com.example.CampusSync.user.model.User;
import com.example.CampusSync.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserRepository userRepository;

    public RefreshTokens createRefreshToken(String email, String deviceInfo) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        RefreshTokens refreshToken = new RefreshTokens();
        refreshToken.setUser(user);
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setIssuedAt(new Timestamp(System.currentTimeMillis()));
        // Expiration in 7 days
        refreshToken.setExpiresAt(new Timestamp(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 7));
        refreshToken.setDeviceInfo(deviceInfo);

        return refreshTokenRepository.save(refreshToken);
    }

    public Optional<RefreshTokens> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshTokens verifyExpiration(RefreshTokens token) {
        if (token.getExpiresAt().before(new Timestamp(System.currentTimeMillis()))) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException("Refresh token was expired. Please make a new signin request");
        }
        if (token.getRevokedAt() != null) {
            throw new RuntimeException("Refresh token has been revoked.");
        }
        return token;
    }

    public void revokeToken(String tokenStr) {
        refreshTokenRepository.findByToken(tokenStr).ifPresent(token -> {
            token.setRevokedAt(new Timestamp(System.currentTimeMillis()));
            refreshTokenRepository.save(token);
        });
    }

    public java.util.List<com.example.CampusSync.refreshtoken.dto.RefreshTokenDTO> getAllTokens() {
        return refreshTokenRepository.findAll().stream()
                .map(com.example.CampusSync.refreshtoken.dto.RefreshTokenDTO::new)
                .collect(java.util.stream.Collectors.toList());
    }

    public Optional<com.example.CampusSync.refreshtoken.dto.RefreshTokenDTO> getTokenById(Long id) {
        return refreshTokenRepository.findById(id)
                .map(com.example.CampusSync.refreshtoken.dto.RefreshTokenDTO::new);
    }

    public java.util.List<com.example.CampusSync.refreshtoken.dto.RefreshTokenDTO> getTokensByUserId(Long userId) {
        return refreshTokenRepository.findByUserId(userId).stream()
                .map(com.example.CampusSync.refreshtoken.dto.RefreshTokenDTO::new)
                .collect(java.util.stream.Collectors.toList());
    }
}
