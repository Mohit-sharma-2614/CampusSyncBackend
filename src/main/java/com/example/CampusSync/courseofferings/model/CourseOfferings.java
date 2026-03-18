package com.example.CampusSync.courseofferings.model;

import com.example.CampusSync.subject.model.Subject;
import com.example.CampusSync.teacher.model.Teacher;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "course_offerings")
public class CourseOfferings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;

    @Column(name = "academic_year", nullable = false)
    private Integer academicYear;

    private Integer semester;

    private String section;

    @CreationTimestamp
    @Column(name = "created_at")
    private Timestamp createdAt;
}
