package com.example.CampusSync.student.entity;

import java.time.LocalDateTime;

import com.example.CampusSync.department.model.Department;

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
