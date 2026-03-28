package com.example.CampusSync.attendance.dto;

import com.example.CampusSync.attendance.model.Attendance;
import com.example.CampusSync.enrollment.dto.EnrollmentDetailsDTO;
import com.example.CampusSync.lecturesessions.dto.LectureSessionsDetailsDTO;
import com.example.CampusSync.attendance_token.dto.AttendanceTokenDetailsDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

import com.example.CampusSync.attendance.model.AttendanceStatus;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AttendanceDetailsDTO {
    private Long id;
    private EnrollmentDetailsDTO enrollment;
    private LectureSessionsDetailsDTO lectureSessions;
    private AttendanceTokenDetailsDTO token;
    private AttendanceStatus status;
    private LocalDateTime createdAt;

    public AttendanceDetailsDTO(Attendance attendance){
        this.id = attendance.getId();
        if (attendance.getEnrollment() != null) {
            this.enrollment = new EnrollmentDetailsDTO(attendance.getEnrollment());
        }
        if (attendance.getLectureSessions() != null) {
            this.lectureSessions = new LectureSessionsDetailsDTO(attendance.getLectureSessions());
        }
        if (attendance.getToke() != null) {
            this.token = new AttendanceTokenDetailsDTO(attendance.getToke());
        }
        this.status = attendance.getStatus();
        this.createdAt = attendance.getCreatedAt();
    }
}
