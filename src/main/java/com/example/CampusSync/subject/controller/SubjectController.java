package com.example.CampusSync.subject.controller;


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

import com.example.CampusSync.subject.dto.SubjectDTO;
import com.example.CampusSync.subject.dto.SubjectDetailsDTO;
import com.example.CampusSync.subject.dto.SubjectInputDTO;
import com.example.CampusSync.subject.service.SubjectServiceImpl;

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

    // TODO: Remove one of the getSubjectById or getSubjectDetailsById as they are same
    @GetMapping("/details/{subjectId}")
    public ResponseEntity<SubjectDetailsDTO> getSubjectDetailsById(@PathVariable Long subjectId) {
        try {
            SubjectDetailsDTO subject = subjectService.getSubjectDetails(subjectId);
            return ResponseEntity.ok(subject);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<SubjectDTO> createSubject(@RequestBody SubjectInputDTO subjectDTO) {
        SubjectDTO created = subjectService.createSubject(subjectDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping
    public ResponseEntity<SubjectDTO> updateSubject(@RequestParam("subjectId") String subjectId, @RequestBody SubjectInputDTO subjectDTO) {
        try {
            SubjectDTO updated = subjectService.updateSubject(Long.parseLong(subjectId), subjectDTO);
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

