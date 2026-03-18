package com.example.CampusSync.refreshtoken.repository;

import com.example.CampusSync.refreshtoken.model.RefreshTokens;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokens, Long> {
    Optional<RefreshTokens> findByToken(String token);
}
