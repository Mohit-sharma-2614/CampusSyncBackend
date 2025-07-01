package com.example.CampusSync.attendance.controller;

import com.example.CampusSync.attendance.dto.AttendanceDTO;
import com.example.CampusSync.attendance.model.Attendance;
import com.example.CampusSync.attendance.service.AttendanceServiceImpl;
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
            @RequestBody Attendance attendance) {
        AttendanceDTO created = attendanceService.createAttendance(attendance);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping
    public ResponseEntity<AttendanceDTO> updateAttendance(
            @RequestBody Attendance attendance) {
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

