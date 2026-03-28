package com.example.CampusSync.enrollment.service;

import java.util.List;

import com.example.CampusSync.enrollment.dto.EnrollmentDTO;
import com.example.CampusSync.enrollment.dto.EnrollmentDetailsDTO;
import com.example.CampusSync.enrollment.dto.EnrollmentInputDTO;

public interface EnrollmentService{
    List<EnrollmentDTO> getAllEnrollment();
    List<EnrollmentDTO> findByStudentId(Long studentId);
    List<EnrollmentDTO> findByCourseOfferingId(Long courseOfferingId);
    List<EnrollmentDTO> findByStudentIdAndCourseOfferingId(Long studentId,Long courseOfferingId);
    EnrollmentDTO getEnrollment(Long enrollmentId);
    EnrollmentDetailsDTO getEnrollmentDetails(Long enrollmentId);
    EnrollmentDTO createEnrollment(EnrollmentInputDTO enrollment);
    EnrollmentDTO updateEnrollment(Long enrollmentId, EnrollmentInputDTO enrollment);
    void deleteEnrollment(Long enrollmentId);
}
