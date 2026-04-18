package com.example.CampusSync.student.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.CampusSync.student.entity.Student;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    
    @Query("SELECT s FROM Student s WHERE s.user.email = :email")
    Optional<Student> findByEmail(@Param("email") String email);

    @Query("SELECT DISTINCT s FROM Student s JOIN Enrollment e ON e.student.id = s.id WHERE e.courseOfferings.subject.id = :subjectId")
    Optional<List<Student>> findBySubjectId(@Param("subjectId") Long subjectId);
}
