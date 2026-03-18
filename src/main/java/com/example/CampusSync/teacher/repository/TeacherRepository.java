package com.example.CampusSync.teacher.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.CampusSync.teacher.model.Teacher;

import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher,Long> {
    
    @Query("SELECT t FROM Teacher t WHERE t.user.email = :email")
    Optional<Teacher> findByEmail(@Param("email") String email);
}
