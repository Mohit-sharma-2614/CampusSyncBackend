package com.example.CampusSync.enrollment.model;

import java.time.LocalDateTime;

import com.example.CampusSync.courseofferings.model.CourseOfferings;
import org.hibernate.annotations.CreationTimestamp;

import com.example.CampusSync.student.entity.Student;
import com.example.CampusSync.subject.model.Subject;

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
@Table(name = "enrollment")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "course_offering_id", nullable = false)
    private CourseOfferings courseOfferings;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
