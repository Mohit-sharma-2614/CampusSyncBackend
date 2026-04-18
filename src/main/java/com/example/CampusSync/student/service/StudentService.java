package com.example.CampusSync.student.service;

import java.util.List;

import com.example.CampusSync.student.dto.StudentResponseDTO;
import com.example.CampusSync.student.dto.StudentLoginDTO;
import com.example.CampusSync.student.dto.StudentRequestDTO;

public interface StudentService {
    List<StudentResponseDTO> getAllStudents();
    StudentResponseDTO getStudent(Long studentId);
    StudentResponseDTO createStudent(StudentRequestDTO studentDto);
    StudentResponseDTO updateStudent(Long studentId, StudentRequestDTO studentDto);
    void deleteStudent(Long studentId);
    StudentResponseDTO verify(StudentLoginDTO studentLoginDTO);
    List<StudentResponseDTO> getStudentsBySubjectId(Long subjectId);
}
