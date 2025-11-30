package com.example.CampusSync.teacher.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.CampusSync.teacher.model.Teacher;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher,Long> {
    Teacher findByEmail(String email);
}
