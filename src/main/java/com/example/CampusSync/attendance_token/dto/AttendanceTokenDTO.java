package com.example.CampusSync.attendance_token.dto;

import java.sql.Timestamp;
import java.util.UUID;

import com.example.CampusSync.attendance_token.model.AttendanceToken;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AttendanceTokenDTO {
    private UUID token;
    private Long lectureSessionId;
    private Timestamp generatedAt;
    private Timestamp expiresAt;

    public AttendanceTokenDTO(AttendanceToken attendanceToken){
        this.token = attendanceToken.getToken();
        if (attendanceToken.getLectureSessions() != null) {
            this.lectureSessionId = attendanceToken.getLectureSessions().getId();
        }
        this.generatedAt = attendanceToken.getGeneratedAt();
        this.expiresAt = attendanceToken.getExpiresAt();
    }
}
