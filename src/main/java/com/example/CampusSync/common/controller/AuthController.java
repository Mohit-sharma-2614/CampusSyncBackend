package com.example.CampusSync.common.controller;

import com.example.CampusSync.common.model.AuthModel;
import com.example.CampusSync.common.security.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final JWTService jwtService;
    private final UserDetailsService userDetailsService;

    @Autowired
    public AuthController(JWTService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
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

}
