package com.example.CampusSync.courseofferings.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.CampusSync.courseofferings.dto.CourseOfferingsDTO;
import com.example.CampusSync.courseofferings.dto.CourseOfferingsDetailsDTO;
import com.example.CampusSync.courseofferings.dto.CourseOfferingsInputDTO;
import com.example.CampusSync.courseofferings.service.CourseOfferingsService;

@RestController
@RequestMapping("/course-offerings")
public class CourseOfferingsController {

    @Autowired
    private CourseOfferingsService service;

    @GetMapping("/all")
    public ResponseEntity<List<CourseOfferingsDTO>> getAllCourseOfferings() {
        return ResponseEntity.ok(service.getAllCourseOfferings());
    }

    @GetMapping
    public ResponseEntity<CourseOfferingsDTO> getCourseOffering(@RequestParam("courseOfferingId") Long id) {
        return ResponseEntity.ok(service.getCourseOffering(id));
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<CourseOfferingsDetailsDTO> getCourseOfferingDetails(@PathVariable Long id) {
        return ResponseEntity.ok(service.getCourseOfferingDetails(id));
    }

    @PostMapping
    public ResponseEntity<CourseOfferingsDTO> createCourseOffering(@RequestBody CourseOfferingsInputDTO inputDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createCourseOffering(inputDTO));
    }

    @PutMapping
    public ResponseEntity<CourseOfferingsDTO> updateCourseOffering(
            @RequestParam("courseOfferingId") Long id,
            @RequestBody CourseOfferingsInputDTO inputDTO) {
        return ResponseEntity.ok(service.updateCourseOffering(id, inputDTO));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCourseOffering(@RequestParam("courseOfferingId") Long id) {
        service.deleteCourseOffering(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/teacher")
    public ResponseEntity<List<CourseOfferingsDTO>> courseOfferingsByTeacherId(@RequestParam("teacherId") Long teacherId) {
        return ResponseEntity.ok(service.courseOfferingsByTeacherId(teacherId));
    }

    @GetMapping("/search")
    public ResponseEntity<List<CourseOfferingsDTO>> getCourseOfferingsBySubjectTeacherAndSemester(
            @RequestParam("subjectId") Long subjectId,
            @RequestParam("teacherId") Long teacherId,
            @RequestParam("semester") Integer semester) {
        return ResponseEntity.ok(service.getCourseOfferingsBySubjectTeacherAndSemester(subjectId, teacherId, semester));
    }

    @GetMapping("/subject-teacher")
    public ResponseEntity<List<CourseOfferingsDTO>> getCourseOfferingsBySubjectAndTeacher(
            @RequestParam("subjectId") Long subjectId,
            @RequestParam("teacherId") Long teacherId) {
        return ResponseEntity.ok(service.getCourseOfferingsBySubjectAndTeacher(subjectId, teacherId));
    }
}
