package com.example.CampusSync.student.entity;

import com.example.CampusSync.attendance.model.Attendance;
import com.example.CampusSync.department.model.Department;
import com.example.CampusSync.enrollment.model.Enrollment;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "students")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "student_uid", unique = true, nullable = false)
    private String student_uid;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    private int semester;

    @ManyToOne(optional = true)
    @JoinColumn(name = "department_id")
    private Department department;

    private LocalDateTime created_at;
}
