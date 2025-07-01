package com.example.CampusSync.attendance_token.service;

import com.example.CampusSync.attendance_token.dto.AttendanceTokenDTO;
import com.example.CampusSync.attendance_token.model.AttendanceToken;
import com.example.CampusSync.attendance_token.repository.AttendanceTokenRepository;
import com.example.CampusSync.common.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class AttendanceTokenServiceImpl implements AttendanceTokenService {

    @Autowired
    private AttendanceTokenRepository attendanceTokenRepository;

    private static final int TOKEN_VALIDITY_MINUTES = 2; // Token valid for 10 minutes

    @Override
    public List<AttendanceTokenDTO> getAllTokens() {
        List<AttendanceToken> attendanceTokens = attendanceTokenRepository.findAll();
        return attendanceTokens.stream()
                .map(AttendanceTokenDTO::new)
                .toList();
    }

    @Override
    public AttendanceTokenDTO getToken(UUID tokenId) {
        AttendanceToken a = attendanceTokenRepository.findById(tokenId)
                .orElseThrow(() -> new ResourceNotFoundException("Token not found with ID: " + tokenId));
        return new AttendanceTokenDTO(a);
    }

    @Override
    public AttendanceTokenDTO createToken(AttendanceToken attendanceToken) {
        Timestamp now = Timestamp.from(Instant.now());
        Timestamp expiry = Timestamp.from(now.toInstant().plus(Duration.ofMinutes(TOKEN_VALIDITY_MINUTES)));

        attendanceToken.setGeneratedAt(now);
        attendanceToken.setExpiresAt(expiry);

        AttendanceToken a = attendanceTokenRepository.save(attendanceToken);
        return new AttendanceTokenDTO(a);
    }

    @Override
    public AttendanceTokenDTO updateToken(AttendanceToken attendanceToken) {
        UUID tokenId = attendanceToken.getToken();

        if (!attendanceTokenRepository.existsById(tokenId)) {
            throw new ResourceNotFoundException("Token not found with ID: " + tokenId);
        }

        // Optionally, update `expiresAt` if needed (e.g., extending expiration)
        // attendanceToken.setExpiresAt(...);

        AttendanceToken a = attendanceTokenRepository.saveAndFlush(attendanceToken);
        return new AttendanceTokenDTO(a);
    }

    @Override
    public void deleteToken(UUID tokenId) {
        if (!attendanceTokenRepository.existsById(tokenId)) {
            throw new ResourceNotFoundException("Cannot delete. Attendance token not found with ID: " + tokenId);
        }

        attendanceTokenRepository.deleteById(tokenId);
    }
}

