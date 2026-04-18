package com.example.CampusSync.attendance.controller;

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

import com.example.CampusSync.attendance.dto.AttendanceDTO;
import com.example.CampusSync.attendance.dto.AttendanceDetailsDTO;
import com.example.CampusSync.attendance.dto.AttendanceInputDTO;
import com.example.CampusSync.attendance.service.AttendanceServiceImpl;
import com.example.CampusSync.common.exceptions.ResourceNotFoundException;

@RestController
@RequestMapping("/attendance")
public class AttendanceController {

    @Autowired
    AttendanceServiceImpl attendanceService;

    @GetMapping("/all")
    public ResponseEntity<List<AttendanceDTO>> getAllAttendance() {
        List<AttendanceDTO> all = attendanceService.getAllAttendance();
        return ResponseEntity.ok(all);
    }

    // TODO: Change the path variable to parameter
    @GetMapping("/lecture-session/{lectureSessionId}")
    public ResponseEntity<List<AttendanceDTO>> getByLectureSession(@PathVariable Long lectureSessionId) {
        try {
            List<AttendanceDTO> attendance = attendanceService.getAttendanceByLectureSessionId(lectureSessionId);
            return ResponseEntity.ok(attendance);
        } catch (NoSuchElementException | ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/enrollment/{enrollmentId}")
    public ResponseEntity<List<AttendanceDTO>> getByEnrollment(@PathVariable Long enrollmentId) {
        try {
            List<AttendanceDTO> attendance = attendanceService.getAttendanceByEnrollmentId(enrollmentId);
            return ResponseEntity.ok(attendance);
        } catch (NoSuchElementException | ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<AttendanceDTO>> createBulkAttendance(
            @RequestBody List<AttendanceInputDTO> attendanceInputs) {
        try {
            List<AttendanceDTO> createdAttendances = attendanceService.createBulkAttendance(attendanceInputs);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdAttendances);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/lecture-session/{lectureSessionId}/enrollment/{enrollmentId}")
    public ResponseEntity<List<AttendanceDTO>> getByLectureSessionAndEnrollment(
            @PathVariable Long lectureSessionId,
            @PathVariable Long enrollmentId) {
        try {
            List<AttendanceDTO> attendance = attendanceService
                    .getAttendanceByLectureSessionAndEnrollmentId(lectureSessionId, enrollmentId);
            return ResponseEntity.ok(attendance);
        } catch (NoSuchElementException | ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/student-subject")
    public ResponseEntity<List<AttendanceDTO>> getAttendanceByStudentAndSubject(
            @RequestParam("studentId") Long studentId,
            @RequestParam("subjectId") Long subjectId) {
        List<AttendanceDTO> attendance = attendanceService.getAttendanceByStudentAndSubject(studentId, subjectId);
        return ResponseEntity.ok(attendance);
    }

    @GetMapping("/subject")
    public ResponseEntity<List<AttendanceDTO>> getAttendanceBySubjectId(
            @RequestParam("subjectId") Long subjectId) {
        List<AttendanceDTO> attendance = attendanceService.getAttendanceBySubjectId(subjectId);
        return ResponseEntity.ok(attendance);
    }

    @GetMapping
    public ResponseEntity<AttendanceDTO> getAttendanceById(
            @RequestParam("attendanceId") String attendanceId) {
        try {
            AttendanceDTO attendance = attendanceService.getAttendance(Long.parseLong(attendanceId));
            return ResponseEntity.ok(attendance);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/details/{attendanceId}")
    public ResponseEntity<AttendanceDetailsDTO> getAttendanceDetailsById(
            @PathVariable Long attendanceId) {
        try {
            AttendanceDetailsDTO attendance = attendanceService.getAttendanceDetails(attendanceId);
            return ResponseEntity.ok(attendance);
        } catch (NoSuchElementException | ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<AttendanceDTO> createAttendance(
            @RequestBody AttendanceInputDTO attendance) {
        AttendanceDTO created = attendanceService.createAttendance(attendance);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping
    public ResponseEntity<AttendanceDTO> updateAttendance(
            @RequestParam("attendanceId") String attendanceId,
            @RequestBody AttendanceInputDTO attendance) {
        try {
            AttendanceDTO updated = attendanceService.updateAttendance(Long.parseLong(attendanceId), attendance);
            return ResponseEntity.ok(updated);
        } catch (NoSuchElementException | ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAttendance(
            @RequestParam("attendanceId") String attendanceId) {
        try {
            attendanceService.deleteAttendance(Long.parseLong(attendanceId));
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (NoSuchElementException | ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
