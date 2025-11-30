package com.example.CampusSync.teacher.service;

import java.util.List;

import com.example.CampusSync.teacher.dto.TeacherDTO;
import com.example.CampusSync.teacher.dto.TeacherRegistrationRequest;
import com.example.CampusSync.teacher.model.Teacher;

public interface TeacherService {
    List<TeacherDTO> getAllTeacher();
    TeacherDTO getTeacher(Long teacherId);
    TeacherDTO updateTeacher(Teacher teacher);
    TeacherDTO createTeacher(TeacherRegistrationRequest teacher);
    void deleteTeacher(Long teacherId);
}
