package com.example.CampusSync.enrollment.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.CampusSync.enrollment.model.Enrollment;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment,Long> {

    @Query("SELECT a FROM Enrollment a WHERE a.student.id = :studentId")
    Optional<List<Enrollment>> findByStudentId(@Param("studentId") Long studentId);

    @Query("SELECT a FROM Enrollment a WHERE a.courseOfferings.id = :courseOfferingId")
    Optional<List<Enrollment>> findByCourseOfferingId(@Param("courseOfferingId") Long courseOfferingId);

    @Query("SELECT a FROM Enrollment a WHERE a.student.id = :studentId AND a.courseOfferings.id = :courseOfferingId")
    Optional<List<Enrollment>> findByStudentIdAndCourseOfferingId(@Param("studentId") Long studentId,@Param("courseOfferingId") Long courseOfferingId);

}
