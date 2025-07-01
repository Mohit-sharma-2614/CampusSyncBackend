package com.example.CampusSync.teacher.repository;

import com.example.CampusSync.teacher.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher,Long> {
    Teacher findByEmail(String email);
}
