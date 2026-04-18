package com.example.CampusSync.lecturesessions.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.CampusSync.lecturesessions.dto.LectureSessionsDTO;
import com.example.CampusSync.lecturesessions.dto.LectureSessionsDetailsDTO;
import com.example.CampusSync.lecturesessions.dto.LectureSessionsInputDTO;
import com.example.CampusSync.lecturesessions.service.LectureSessionsService;

@RestController
@RequestMapping("/lecturesessions")
public class LectureSessionsController {

    @Autowired
    private LectureSessionsService lectureSessionsService;

    @GetMapping("/all")
    public ResponseEntity<List<LectureSessionsDTO>> getAllLectureSessions() {
        List<LectureSessionsDTO> sessions = lectureSessionsService.getAllLectureSessions();
        return ResponseEntity.ok(sessions);
    }

    @GetMapping
    public ResponseEntity<LectureSessionsDTO> getLectureSessionById(@RequestParam("sessionId") String sessionId) {
        try {
            LectureSessionsDTO session = lectureSessionsService.getLectureSession(Long.parseLong(sessionId));
            return ResponseEntity.ok(session);
        } catch (NoSuchElementException | NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/details/{sessionId}")
    public ResponseEntity<LectureSessionsDetailsDTO> getLectureSessionDetailsById(@PathVariable Long sessionId) {
        try {
            LectureSessionsDetailsDTO session = lectureSessionsService.getLectureSessionDetails(sessionId);
            return ResponseEntity.ok(session);
        } catch (NoSuchElementException | NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/course-offering")
    public ResponseEntity<List<LectureSessionsDTO>> getLectureSessionsByCourseOfferingId(
            @RequestParam("courseOfferingId") Long courseOfferingId) {
        List<LectureSessionsDTO> sessions = lectureSessionsService.getLectureSessionsByCourseOfferingId(courseOfferingId);
        return ResponseEntity.ok(sessions);
    }

    @PostMapping
    public ResponseEntity<LectureSessionsDTO> createLectureSession(@RequestBody LectureSessionsInputDTO inputDTO) {
        LectureSessionsDTO created = lectureSessionsService.createLectureSession(inputDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping
    public ResponseEntity<LectureSessionsDTO> updateLectureSession(
            @RequestParam("sessionId") String sessionId,
            @RequestBody LectureSessionsInputDTO inputDTO) {
        try {
            LectureSessionsDTO updated = lectureSessionsService.updateLectureSession(Long.parseLong(sessionId), inputDTO);
            return ResponseEntity.ok(updated);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteLectureSession(@RequestParam("sessionId") String sessionId) {
        try {
            lectureSessionsService.deleteLectureSession(Long.parseLong(sessionId));
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException | NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
