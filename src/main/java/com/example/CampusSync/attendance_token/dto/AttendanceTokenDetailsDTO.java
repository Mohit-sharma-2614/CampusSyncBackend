package com.example.CampusSync.attendance_token.dto;

import java.sql.Timestamp;
import java.util.UUID;

import com.example.CampusSync.attendance_token.model.AttendanceToken;
import com.example.CampusSync.lecturesessions.dto.LectureSessionsDetailsDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AttendanceTokenDetailsDTO {
    private UUID token;
    private LectureSessionsDetailsDTO lectureSessions;
    private Timestamp generatedAt;
    private Timestamp expiresAt;

    public AttendanceTokenDetailsDTO(AttendanceToken attendanceToken){
        this.token = attendanceToken.getToken();
        if (attendanceToken.getLectureSessions() != null) {
            this.lectureSessions = new LectureSessionsDetailsDTO(attendanceToken.getLectureSessions());
        }
        this.generatedAt = attendanceToken.getGeneratedAt();
        this.expiresAt = attendanceToken.getExpiresAt();
    }
}
