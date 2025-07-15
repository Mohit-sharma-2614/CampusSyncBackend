package com.example.CampusSync.enrollment.controller;


import com.example.CampusSync.enrollment.dto.EnrollmentDTO;
import com.example.CampusSync.enrollment.dto.EnrollmentInputDTO;
import com.example.CampusSync.enrollment.model.Enrollment;
import com.example.CampusSync.enrollment.service.EnrollmentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/enrollment")
public class EnrollmentController {

    @Autowired
    EnrollmentServiceImpl enrollmentService;

    @GetMapping("/all")
    public ResponseEntity<List<EnrollmentDTO>> getAllEnrollments() {
        List<EnrollmentDTO> enrollments = enrollmentService.getAllEnrollment();
        return ResponseEntity.ok(enrollments);
    }

    @GetMapping("/student")
    public ResponseEntity<List<EnrollmentDTO>> getEnrollmentsByStudentId(
            @RequestParam("studentId") Long studentId) {
        try {
            List<EnrollmentDTO> enrollments = enrollmentService.findByStudentId(studentId);
            return ResponseEntity.ok(enrollments);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/subject")
    public ResponseEntity<List<EnrollmentDTO>> getEnrollmentsBySubjectId(
            @RequestParam("subjectId") Long subjectId) {
        try {
            List<EnrollmentDTO> enrollments = enrollmentService.findBySubjectId(subjectId);
            return ResponseEntity.ok(enrollments);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/student-subject")
    public ResponseEntity<List<EnrollmentDTO>> getEnrollmentsByStudentIdAndSubjectId(
            @RequestParam("studentId") Long studentId,
            @RequestParam("subjectId") Long subjectId) {
        try {
            List<EnrollmentDTO> enrollments = enrollmentService.findByStudentIdAndSubjectId(studentId, subjectId);
            return ResponseEntity.ok(enrollments);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<EnrollmentDTO> getEnrollmentById(
            @RequestParam("enrollmentId") String enrollmentId) {
        try {
            EnrollmentDTO enrollment = enrollmentService.getEnrollment(Long.parseLong(enrollmentId));
            return ResponseEntity.ok(enrollment);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<EnrollmentDTO> createEnrollment(
            @RequestBody EnrollmentInputDTO enrollment) {
        EnrollmentDTO created = enrollmentService.createEnrollment(enrollment);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping
    public ResponseEntity<EnrollmentDTO> updateEnrollment(
            @RequestBody EnrollmentInputDTO enrollment) {
        try {
            EnrollmentDTO updated = enrollmentService.updateEnrollment(enrollment);
            return ResponseEntity.ok(updated);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteEnrollment(
            @RequestParam("enrollmentId") String enrollmentId) {
        try {
            enrollmentService.deleteEnrollment(Long.parseLong(enrollmentId));
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
