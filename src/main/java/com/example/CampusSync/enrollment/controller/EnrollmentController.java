package com.example.CampusSync.enrollment.controller;


import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.CampusSync.enrollment.dto.EnrollmentDTO;
import com.example.CampusSync.enrollment.dto.EnrollmentDetailsDTO;
import com.example.CampusSync.enrollment.dto.EnrollmentInputDTO;
import com.example.CampusSync.enrollment.service.EnrollmentServiceImpl;

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

    @GetMapping("/course-offering")
    public ResponseEntity<List<EnrollmentDTO>> getEnrollmentsByCourseOfferingId(
            @RequestParam("courseOfferingId") Long courseOfferingId) {
        try {
            List<EnrollmentDTO> enrollments = enrollmentService.findByCourseOfferingId(courseOfferingId);
            return ResponseEntity.ok(enrollments);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/student-course-offering")
    public ResponseEntity<List<EnrollmentDTO>> getEnrollmentsByStudentIdAndCourseOfferingId(
            @RequestParam("studentId") Long studentId,
            @RequestParam("courseOfferingId") Long courseOfferingId) {
        try {
            List<EnrollmentDTO> enrollments = enrollmentService.findByStudentIdAndCourseOfferingId(studentId, courseOfferingId);
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

    @GetMapping("/details/{enrollmentId}")
    public ResponseEntity<EnrollmentDetailsDTO> getEnrollmentDetailsById(
            @PathVariable Long enrollmentId) {
        try {
            EnrollmentDetailsDTO enrollment = enrollmentService.getEnrollmentDetails(enrollmentId);
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
            @RequestParam("enrollmentId") String enrollmentId,
            @RequestBody EnrollmentInputDTO enrollment) {
        try {
            EnrollmentDTO updated = enrollmentService.updateEnrollment(Long.parseLong(enrollmentId), enrollment);
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
