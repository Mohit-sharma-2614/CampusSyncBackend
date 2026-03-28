package com.example.CampusSync.refreshtoken.controller;

import com.example.CampusSync.refreshtoken.dto.RefreshTokenDTO;
import com.example.CampusSync.refreshtoken.service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/refreshtokens")
public class RefreshTokenController {

    @Autowired
    private RefreshTokenService refreshTokenService;

    @GetMapping("/all")
    public ResponseEntity<List<RefreshTokenDTO>> getAllTokens() {
        return ResponseEntity.ok(refreshTokenService.getAllTokens());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RefreshTokenDTO> getTokenById(@PathVariable Long id) {
        Optional<RefreshTokenDTO> token = refreshTokenService.getTokenById(id);
        return token.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<RefreshTokenDTO>> getTokensByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(refreshTokenService.getTokensByUserId(userId));
    }
}
