package com.example.CampusSync.attendance_token.model;

import com.example.CampusSync.subject.model.Subject;
import com.example.CampusSync.teacher.model.Teacher;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.UUID;

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
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;

    @Column(name = "generated_at", nullable = false)
    private Timestamp generatedAt;

    @Column(name = "expires_at", nullable = false)
    private Timestamp expiresAt;
}
