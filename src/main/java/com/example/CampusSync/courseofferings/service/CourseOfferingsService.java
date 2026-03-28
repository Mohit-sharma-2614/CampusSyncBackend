package com.example.CampusSync.courseofferings.service;

import java.util.List;

import com.example.CampusSync.courseofferings.dto.CourseOfferingsDTO;
import com.example.CampusSync.courseofferings.dto.CourseOfferingsDetailsDTO;
import com.example.CampusSync.courseofferings.dto.CourseOfferingsInputDTO;

public interface CourseOfferingsService {
    List<CourseOfferingsDTO> getAllCourseOfferings();
    CourseOfferingsDTO getCourseOffering(Long id);
    CourseOfferingsDetailsDTO getCourseOfferingDetails(Long id);
    CourseOfferingsDTO createCourseOffering(CourseOfferingsInputDTO inputDTO);
    CourseOfferingsDTO updateCourseOffering(Long id, CourseOfferingsInputDTO inputDTO);
    void deleteCourseOffering(Long id);
}
