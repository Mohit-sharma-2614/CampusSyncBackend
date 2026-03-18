package com.example.CampusSync.student.entity;

import java.time.LocalDateTime;

import com.example.CampusSync.user.model.User;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import com.example.CampusSync.department.model.Department;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "students")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    @Id
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "roll_number", unique = true, nullable = false)
    private String rollNumber;

    @Column(name = "department_id", nullable = false)
    private String departmentId;

    private Integer year;

    private Integer semester;

    private String section;
}
