package com.example.CampusSync.attendance_token.service;

import com.example.CampusSync.attendance_token.dto.AttendanceTokenDTO;
import com.example.CampusSync.attendance_token.dto.AttendanceTokenDetailsDTO;
import com.example.CampusSync.attendance_token.dto.AttendanceTokenInputDTO;
import com.example.CampusSync.attendance_token.model.AttendanceToken;

import java.util.List;
import java.util.UUID;

public interface AttendanceTokenService {
    List<AttendanceTokenDTO> getAllTokens();
    AttendanceTokenDTO getToken(UUID tokenId);
    AttendanceTokenDetailsDTO getTokenDetails(UUID tokenId);
    AttendanceTokenDTO createToken(AttendanceTokenInputDTO attendanceToken);
    AttendanceTokenDTO updateToken(AttendanceToken attendanceToken);
    void deleteToken(UUID tokenId);
}
