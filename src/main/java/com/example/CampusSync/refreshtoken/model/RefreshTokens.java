package com.example.CampusSync.refreshtoken.model;

import com.example.CampusSync.user.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "refresh_tokens")
@Entity
public class RefreshTokens {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(name = "issued_at", nullable = false)
    private Timestamp issuedAt;

    @Column(name = "expires_at", nullable = false)
    private Timestamp expiresAt;

    @Column(name = "revoked_at")
    private Timestamp revokedAt;

    @Column(name = "device_info")
    private String deviceInfo;
}
