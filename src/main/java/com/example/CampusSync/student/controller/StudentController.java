package com.example.CampusSync.student.controller;

import com.example.CampusSync.common.exceptions.BadCredentialsException;
import com.example.CampusSync.student.dto.StudentDTO;
import com.example.CampusSync.student.dto.StudentLoginDTO;
import com.example.CampusSync.student.entity.Student;
import com.example.CampusSync.student.service.StudentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    StudentServiceImpl studentService;

    @PostMapping("/login")
    public ResponseEntity<?> loginStudent(@RequestBody StudentLoginDTO student){
        try {
            StudentDTO s = studentService.verify(student);
            return ResponseEntity.ok(s);
        } catch (UsernameNotFoundException | BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerStudent(@RequestBody Student student){
        StudentDTO s = studentService.createStudent(student);
        if (s != null){
            return ResponseEntity.ok(s);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }


    @GetMapping("/all")
    public ResponseEntity<List<StudentDTO>> getAllStudents() {
        List<StudentDTO> students = studentService.getAllStudents();
        return ResponseEntity.ok(students);
    }

    @GetMapping
    public ResponseEntity<StudentDTO> getStudentById(@RequestParam("studentId") String studentId) {
        try {
            StudentDTO student = studentService.getStudent(Long.parseLong(studentId));
            return ResponseEntity.ok(student);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<StudentDTO> createStudent(@RequestBody Student student) {
        StudentDTO created = studentService.createStudent(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping
    public ResponseEntity<StudentDTO> updateStudent(@RequestBody Student student) {
        try {
            StudentDTO updated = studentService.updateStudent(student);
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

