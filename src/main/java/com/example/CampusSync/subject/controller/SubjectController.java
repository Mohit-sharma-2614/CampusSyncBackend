package com.example.CampusSync.subject.controller;


import com.example.CampusSync.subject.dto.SubjectDTO;
import com.example.CampusSync.subject.model.Subject;
import com.example.CampusSync.subject.service.SubjectServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/subject")
public class SubjectController {

    @Autowired
    SubjectServiceImpl subjectService;

    @GetMapping("/all")
    public ResponseEntity<List<SubjectDTO>> getAllSubjects() {
        List<SubjectDTO> subjects = subjectService.getAllSubjects();
        return ResponseEntity.ok(subjects);
    }

    @GetMapping
    public ResponseEntity<SubjectDTO> getSubjectById(@RequestParam("subjectId") String subjectId) {
        try {
            SubjectDTO subject = subjectService.getSubject(Long.parseLong(subjectId));
            return ResponseEntity.ok(subject);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<SubjectDTO> createSubject(@RequestBody Subject subject) {
        SubjectDTO created = subjectService.createSubject(subject);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping
    public ResponseEntity<SubjectDTO> updateSubject(@RequestBody Subject subject) {
        try {
            SubjectDTO updated = subjectService.updateSubject(subject);
            return ResponseEntity.ok(updated);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteSubject(@RequestParam("subjectId") String subjectId) {
        try {
            subjectService.deleteSubject(Long.parseLong(subjectId));
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}

