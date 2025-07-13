package com.example.CampusSync.attendance.repository;


import com.example.CampusSync.attendance.model.Attendance;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@ComponentScan
public interface AttendanceRepository extends JpaRepository<Attendance,Long> {
    @Query("SELECT a FROM Attendance a WHERE a.subject.id = :subjectId")
    List<Attendance> findBySubjectId(@Param("subjectId") Long subjectId);

    @Query("SELECT a FROM Attendance a WHERE a.student.id = :studentId")
    List<Attendance> findByStudentId(@Param("studentId") Long studentId);

    @Query("SELECT a FROM Attendance a WHERE a.subject.id = :subjectId AND a.student.id = :studentId")
    List<Attendance> findBySubjectIdAndStudentId(@Param("subjectId") Long subjectId, @Param("studentId") Long studentId);
}
