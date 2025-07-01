package com.example.CampusSync.subject.model;

import com.example.CampusSync.attendance.model.Attendance;
import com.example.CampusSync.attendance_token.model.AttendanceToken;
import com.example.CampusSync.department.model.Department;
import com.example.CampusSync.enrollment.model.Enrollment;
import com.example.CampusSync.teacher.model.Teacher;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "subjects")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true, nullable = false)
    private String code;

    private String semester;

    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
