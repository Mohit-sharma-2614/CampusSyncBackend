package com.example.CampusSync.teacher.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.CampusSync.teacher.dto.TeacherResponseDTO;
import com.example.CampusSync.teacher.dto.TeacherLoginDTO;
import com.example.CampusSync.teacher.dto.TeacherRequestDTO;
import com.example.CampusSync.teacher.service.TeacherServiceImpl;
import com.example.CampusSync.common.exceptions.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private TeacherServiceImpl teacherService;


    @GetMapping("/all")
    public ResponseEntity<List<TeacherResponseDTO>> getAllTeachers() {
        List<TeacherResponseDTO> teachers = teacherService.getAllTeacher();
        return ResponseEntity.ok(teachers);
    }

    @GetMapping
    public ResponseEntity<TeacherResponseDTO> getTeacherById(@RequestParam("teacherId") String teacherId) {
        try {
            TeacherResponseDTO teacher = teacherService.getTeacher(Long.parseLong(teacherId));
            return ResponseEntity.ok(teacher);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginTeacher(@RequestBody TeacherLoginDTO teacher) {
        try {
            TeacherResponseDTO t = teacherService.verify(teacher);
            return ResponseEntity.ok(t);
        } catch (UsernameNotFoundException | BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerTeacher(@RequestBody TeacherRequestDTO teacher) {
        try {
            TeacherResponseDTO created = teacherService.createTeacher(teacher);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<TeacherResponseDTO> updateTeacher(@RequestParam("teacherId") String teacherId, @RequestBody TeacherRequestDTO teacher) {
        try {
            TeacherResponseDTO updated = teacherService.updateTeacher(Long.parseLong(teacherId), teacher);
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
