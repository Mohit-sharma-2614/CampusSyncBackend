package com.example.CampusSync.enrollment.service;

import java.util.List;

import com.example.CampusSync.enrollment.dto.EnrollmentDTO;
import com.example.CampusSync.enrollment.dto.EnrollmentInputDTO;

public interface EnrollmentService{
    List<EnrollmentDTO> getAllEnrollment();
    List<EnrollmentDTO> findByStudentId(Long studentId);
    List<EnrollmentDTO> findBySubjectId(Long subjectId);
    List<EnrollmentDTO> findByStudentIdAndSubjectId(Long studentId,Long subjectId);
    EnrollmentDTO getEnrollment(Long enrollmentId);
    EnrollmentDTO createEnrollment(EnrollmentInputDTO enrollment);
    EnrollmentDTO updateEnrollment(EnrollmentInputDTO enrollment);
    void deleteEnrollment(Long enrollmentId);
}
