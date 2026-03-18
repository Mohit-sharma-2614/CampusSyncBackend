package com.example.CampusSync.attendance_token.model;

import java.sql.Timestamp;
import java.util.UUID;

import com.example.CampusSync.lecturesessions.model.LectureSessions;
import com.example.CampusSync.subject.model.Subject;
import com.example.CampusSync.teacher.model.Teacher;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "attendance_tokens")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceToken {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID token;

    @ManyToOne
    @JoinColumn(name = "lecture_session_id", nullable = false)
    private LectureSessions lectureSessions;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "generated_at", nullable = false)
    private Timestamp generatedAt;

    @Column(name = "expires_at", nullable = false)
    private Timestamp expiresAt;
}
