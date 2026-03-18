package com.example.CampusSync.teacher.service;

import java.util.List;

import com.example.CampusSync.teacher.dto.TeacherResponseDTO;
import com.example.CampusSync.teacher.dto.TeacherLoginDTO;
import com.example.CampusSync.teacher.dto.TeacherRequestDTO;

public interface TeacherService {
    List<TeacherResponseDTO> getAllTeacher();
    TeacherResponseDTO getTeacher(Long teacherId);
    TeacherResponseDTO updateTeacher(Long teacherId, TeacherRequestDTO teacher);
    TeacherResponseDTO createTeacher(TeacherRequestDTO teacher);
    void deleteTeacher(Long teacherId);
    TeacherResponseDTO verify(TeacherLoginDTO teacherLoginDTO);
}
