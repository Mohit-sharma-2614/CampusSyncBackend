package com.example.CampusSync.enrollment.service;

import com.example.CampusSync.enrollment.dto.EnrollmentDTO;
import com.example.CampusSync.enrollment.model.Enrollment;

import java.util.List;

public interface EnrollmentService{
    List<EnrollmentDTO> getAllEnrollment();
    EnrollmentDTO getEnrollment(Long enrollmentId);
    EnrollmentDTO createEnrollment(Enrollment enrollment);
    EnrollmentDTO updateEnrollment(Enrollment enrollment);
    void deleteEnrollment(Long enrollmentId);
}
