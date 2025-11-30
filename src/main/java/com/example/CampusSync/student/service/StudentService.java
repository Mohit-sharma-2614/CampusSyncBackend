package com.example.CampusSync.student.service;

import java.util.List;

import com.example.CampusSync.student.dto.StudentDTO;
import com.example.CampusSync.student.entity.Student;

public interface StudentService {
    List<StudentDTO> getAllStudents();
    StudentDTO getStudent(Long studentId);
    StudentDTO createStudent(Student student);
    StudentDTO updateStudent(Student student);
    void deleteStudent(Long studentId);
}
