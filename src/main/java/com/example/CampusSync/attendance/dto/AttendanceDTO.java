package com.example.CampusSync.attendance.dto;

import com.example.CampusSync.attendance.model.Attendance;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

import com.example.CampusSync.attendance.model.AttendanceStatus;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AttendanceDTO {
    private Long id;
    private Long enrollmentId;
    private Long lectureSessionId;
    private UUID tokenId;
    private AttendanceStatus status;
    private LocalDateTime createdAt;

    public AttendanceDTO(Attendance attendance){
        this.id = attendance.getId();
        if (attendance.getEnrollment() != null) {
            this.enrollmentId = attendance.getEnrollment().getId();
        }
        if (attendance.getLectureSessions() != null) {
            this.lectureSessionId = attendance.getLectureSessions().getId();
        }
        if (attendance.getToke() != null) {
            this.tokenId = attendance.getToke().getToken();
        }
        this.status = attendance.getStatus();
        this.createdAt = attendance.getCreatedAt();
    }
}
