package com.example.CampusSync.refreshtoken.dto;

import com.example.CampusSync.refreshtoken.model.RefreshTokens;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
public class RefreshTokenDTO {
    private Long id;
    private Long userId;
    private String token;
    private Timestamp issuedAt;
    private Timestamp expiresAt;
    private Timestamp revokedAt;
    private String deviceInfo;

    public RefreshTokenDTO(RefreshTokens refreshToken) {
        if (refreshToken != null) {
            this.id = refreshToken.getId();
            if (refreshToken.getUser() != null) {
                this.userId = refreshToken.getUser().getId();
            }
            this.token = refreshToken.getToken();
            this.issuedAt = refreshToken.getIssuedAt();
            this.expiresAt = refreshToken.getExpiresAt();
            this.revokedAt = refreshToken.getRevokedAt();
            this.deviceInfo = refreshToken.getDeviceInfo();
        }
    }
}
