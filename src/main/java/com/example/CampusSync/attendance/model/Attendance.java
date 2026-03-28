package com.example.CampusSync.attendance.model;


import java.time.LocalDateTime;

import com.example.CampusSync.attendance_token.model.AttendanceToken;
import com.example.CampusSync.enrollment.model.Enrollment;
import com.example.CampusSync.lecturesessions.model.LectureSessions;
import org.hibernate.annotations.CreationTimestamp;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


enum Status{
    ABSENT,
    PRESENT
}

@Entity
@Table(name = "attendance")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "enrollment_id", nullable = false)
    private Enrollment enrollment;

    @ManyToOne
    @JoinColumn(name = "lecture_session_id", nullable = false)
    private LectureSessions lectureSessions;

    @ManyToOne
    @JoinColumn(name = "token_id", nullable = false)
    private AttendanceToken toke;

    @Enumerated(EnumType.STRING)
    private AttendanceStatus status;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
