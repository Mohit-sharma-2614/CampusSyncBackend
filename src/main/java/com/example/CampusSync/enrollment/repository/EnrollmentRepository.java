package com.example.CampusSync.enrollment.repository;

import com.example.CampusSync.enrollment.model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment,Long> {

    @Query("SELECT a FROM Enrollment a WHERE a.student.id = :studentId")
    Optional<List<Enrollment>> findByStudentId(@Param("studentId") Long studentId);

    @Query("SELECT a FROM Enrollment a WHERE a.subject.id = :subjectId")
    Optional<List<Enrollment>> findBySubjectId(@Param("subjectId") Long subjectId);

    @Query("SELECT a FROM Enrollment a WHERE a.student.id = :studentId AND a.subject.id = :subjectId")
    Optional<List<Enrollment>> findByStudentIdAndSubjectId(@Param("studentId") Long studentId,@Param("subjectId") Long subjectId);

}
