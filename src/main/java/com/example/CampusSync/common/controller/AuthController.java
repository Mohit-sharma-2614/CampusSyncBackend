package com.example.CampusSync.common.controller;

import com.example.CampusSync.common.model.TokenRefreshRequest;
import com.example.CampusSync.common.model.TokenRefreshResponse;
import com.example.CampusSync.refreshtoken.model.RefreshTokens;
import com.example.CampusSync.refreshtoken.service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import com.example.CampusSync.common.model.AuthModel;
import com.example.CampusSync.common.security.JWTService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final JWTService jwtService;
    private final UserDetailsService userDetailsService;
    private final RefreshTokenService refreshTokenService;

    @Autowired
    public AuthController(JWTService jwtService, UserDetailsService userDetailsService, RefreshTokenService refreshTokenService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/validate-token")
    public ResponseEntity<AuthModel> validateToken(@RequestHeader("Authorization") String authHeader) {
        try {
            // Check for missing or malformed token
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity
                        .badRequest()
                        .body(new AuthModel(false, "Invalid Authorization header. Expected 'Bearer <token>'"));
            }

            // Extract token
            String token = authHeader.substring(7);

            // Extract email
            String email = jwtService.extractEmail(token);

            // Load user from DB
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);

            // Validate token
            boolean isValid = jwtService.validateToken(token, userDetails);

            if (isValid) {
                return ResponseEntity.ok(new AuthModel(true, "Token is valid."));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new AuthModel(false, "Token is invalid or expired."));
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthModel(false, "Token validation failed: " + e.getMessage()));
        }
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshtoken(@RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        try {
            return refreshTokenService.findByToken(requestRefreshToken)
                    .map(refreshTokenService::verifyExpiration)
                    .map(RefreshTokens::getUser)
                    .map(user -> {
                        String token = jwtService.generateToken(user.getEmail(), user.getRole().name());
                        return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
                    })
                    .orElseThrow(() -> new RuntimeException("Refresh token is not in database!"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new AuthModel(false, e.getMessage()));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody TokenRefreshRequest request) {
        try {
            refreshTokenService.revokeToken(request.getRefreshToken());
            return ResponseEntity.ok(new AuthModel(true, "Log out successful"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new AuthModel(false, "Logout failed: " + e.getMessage()));
        }
    }
}
