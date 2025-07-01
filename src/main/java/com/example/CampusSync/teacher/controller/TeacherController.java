package com.example.CampusSync.teacher.controller;


import com.example.CampusSync.teacher.dto.TeacherDTO;
import com.example.CampusSync.teacher.dto.TeacherRegistrationRequest;
import com.example.CampusSync.teacher.model.Teacher;
import com.example.CampusSync.teacher.service.TeacherServiceImpl;
import jakarta.persistence.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private TeacherServiceImpl teacherService;


    @GetMapping("/all")
    public ResponseEntity<List<TeacherDTO>> getAllTeachers() {
        List<TeacherDTO> teachers = teacherService.getAllTeacher();
        return ResponseEntity.ok(teachers);
    }

    @GetMapping
    public ResponseEntity<TeacherDTO> getTeacherById(@RequestParam("teacherId") String teacherId) {
        try {
            TeacherDTO teacher = teacherService.getTeacher(Long.parseLong(teacherId));
            return ResponseEntity.ok(teacher);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginTeacher(@RequestBody Teacher teacher) {
        TeacherDTO t = teacherService.verify(teacher);
        return ResponseEntity.ok(t);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerTeacher(@RequestBody TeacherRegistrationRequest teacher) {
        TeacherDTO created = teacherService.createTeacher(teacher);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping
    public ResponseEntity<TeacherDTO> updateTeacher(@RequestBody Teacher teacher) {
        try {
            TeacherDTO updated = teacherService.updateTeacher(teacher);
            return ResponseEntity.ok(updated);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteTeacher(@RequestParam("teacherId") String teacherId) {
        try {
            teacherService.deleteTeacher(Long.parseLong(teacherId));
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
