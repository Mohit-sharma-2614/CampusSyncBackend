package com.example.CampusSync.attendance.controller;

import com.example.CampusSync.attendance.dto.AttendanceDTO;
import com.example.CampusSync.attendance.dto.AttendanceInputDTO;
import com.example.CampusSync.attendance.model.Attendance;
import com.example.CampusSync.attendance.service.AttendanceServiceImpl;
import com.example.CampusSync.common.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

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

    @GetMapping("/subject/{subjectId}")
    public ResponseEntity<List<AttendanceDTO>> getBySubject(@PathVariable Long subjectId) {

        try{
            List<AttendanceDTO> attendance = attendanceService.getAttendanceBySubjectId(subjectId);
            return ResponseEntity.ok(attendance);
        } catch (NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<AttendanceDTO>> getByStudent(@PathVariable Long studentId) {
        try{
            List<AttendanceDTO> attendance = attendanceService.getAttendanceByStudentId(studentId);
            return ResponseEntity.ok(attendance);
        } catch (NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<AttendanceDTO>> createBulkAttendance(@RequestBody List<AttendanceInputDTO> attendanceInputs) {
        try {
            List<AttendanceDTO> createdAttendances = attendanceService.createBulkAttendance(attendanceInputs);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdAttendances);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/subject-date")
    public ResponseEntity<List<AttendanceDTO>> getAttendanceBySubjectAndDate(
            @RequestParam("subjectId") Long subjectId,
            @RequestParam("date") String date) {
        try {
            List<AttendanceDTO> attendances = attendanceService.getAttendanceBySubjectAndDate(subjectId, date);
            return ResponseEntity.ok(attendances);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/subject/{subjectId}/student/{studentId}")
    public ResponseEntity<List<AttendanceDTO>> getBySubjectAndStudent(
            @PathVariable Long subjectId,
            @PathVariable Long studentId) {
        try{
            List<AttendanceDTO> attendance = attendanceService.getAttendanceBySubjectAndStudentId(subjectId,studentId);
            return ResponseEntity.ok(attendance);
        } catch (NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
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

    @PostMapping
    public ResponseEntity<AttendanceDTO> createAttendance(
            @RequestBody AttendanceInputDTO attendance) {
        AttendanceDTO created = attendanceService.createAttendance(attendance);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping
    public ResponseEntity<AttendanceDTO> updateAttendance(
            @RequestBody AttendanceInputDTO attendance) {
        try {
            AttendanceDTO updated = attendanceService.updateAttendance(attendance);
            return ResponseEntity.ok(updated);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAttendance(
            @RequestParam("attendanceId") String attendanceId) {
        try {
            attendanceService.deleteAttendance(Long.parseLong(attendanceId));
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}

