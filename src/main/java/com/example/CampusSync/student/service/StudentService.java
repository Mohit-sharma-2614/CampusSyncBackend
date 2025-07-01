package com.example.CampusSync.student.service;

import com.example.CampusSync.student.dto.StudentDTO;
import com.example.CampusSync.student.entity.Student;

import java.util.List;

public interface StudentService {
    List<StudentDTO> getAllStudents();
    StudentDTO getStudent(Long studentId);
    StudentDTO createStudent(Student student);
    StudentDTO updateStudent(Student student);
    void deleteStudent(Long studentId);
}
