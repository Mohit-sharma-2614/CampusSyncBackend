package com.example.CampusSync.lecturesessions.model;

import com.example.CampusSync.courseofferings.model.CourseOfferings;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Date;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "lecture_sessions")
@Data
public class LectureSessions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "course_offering_id", nullable = false)
    private CourseOfferings courseOfferings;

    @Column(name = "session_date", nullable = false)
    private Date sessionDate;

    @Column(name = "start_time", nullable = false)
    private Timestamp startTime;

    @Column(name = "end_time", nullable = false)
    private Timestamp endTime;

    private String room;

    private String topic;

    @CreationTimestamp
    @Column(name = "created_at")
    private Timestamp createdAt;

}
