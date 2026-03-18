package com.example.CampusSync.student.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.CampusSync.common.exceptions.BadCredentialsException;
import com.example.CampusSync.student.dto.StudentResponseDTO;
import com.example.CampusSync.student.dto.StudentLoginDTO;
import com.example.CampusSync.student.dto.StudentRequestDTO;
import com.example.CampusSync.student.service.StudentServiceImpl;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentServiceImpl studentService;

    @PostMapping("/login")
    public ResponseEntity<?> loginStudent(@RequestBody StudentLoginDTO student){
        try {
            StudentResponseDTO s = studentService.verify(student);
            return ResponseEntity.ok(s);
        } catch (UsernameNotFoundException | BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerStudent(@RequestBody StudentRequestDTO studentRegisterDto){
        try {
            StudentResponseDTO s = studentService.createStudent(studentRegisterDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(s);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }
    }


    @GetMapping("/all")
    public ResponseEntity<List<StudentResponseDTO>> getAllStudents() {
        List<StudentResponseDTO> students = studentService.getAllStudents();
        return ResponseEntity.ok(students);
    }

    @GetMapping
    public ResponseEntity<StudentResponseDTO> getStudentById(@RequestParam("studentId") String studentId) {
        try {
            StudentResponseDTO student = studentService.getStudent(Long.parseLong(studentId));
            return ResponseEntity.ok(student);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping
    public ResponseEntity<StudentResponseDTO> updateStudent(@RequestParam("studentId") String studentId, @RequestBody StudentRequestDTO student) {
        try {
            StudentResponseDTO updated = studentService.updateStudent(Long.parseLong(studentId), student);
            return ResponseEntity.ok(updated);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteStudent(@RequestParam("studentId") String studentId) {
        try {
            studentService.deleteStudent(Long.parseLong(studentId));
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
