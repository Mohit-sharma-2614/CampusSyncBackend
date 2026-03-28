package com.example.CampusSync.attendance.repository;

import java.util.List;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.CampusSync.attendance.model.Attendance;

@Repository
@ComponentScan
public interface AttendanceRepository extends JpaRepository<Attendance,Long> {
    @Query("SELECT a FROM Attendance a WHERE a.lectureSessions.id = :lectureSessionId")
    List<Attendance> findByLectureSessionId(@Param("lectureSessionId") Long lectureSessionId);

    @Query("SELECT a FROM Attendance a WHERE a.enrollment.id = :enrollmentId")
    List<Attendance> findByEnrollmentId(@Param("enrollmentId") Long enrollmentId);

    @Query("SELECT a FROM Attendance a WHERE a.lectureSessions.id = :lectureSessionId AND a.enrollment.id = :enrollmentId")
    List<Attendance> findByLectureSessionIdAndEnrollmentId(@Param("lectureSessionId") Long lectureSessionId, @Param("enrollmentId") Long enrollmentId);
}
