package com.example.CampusSync.enrollment.service;

import com.example.CampusSync.enrollment.dto.EnrollmentDTO;
import com.example.CampusSync.enrollment.model.Enrollment;
import com.example.CampusSync.enrollment.repository.EnrollmentRepository;
import com.example.CampusSync.common.exceptions.ResourceAlreadyExistException;
import com.example.CampusSync.common.exceptions.ResourceNotFoundException;
import com.example.CampusSync.teacher.dto.TeacherDTO;
import com.example.CampusSync.teacher.model.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@ComponentScan
@Service
public class EnrollmentServiceImpl implements EnrollmentService{
    @Autowired
    EnrollmentRepository enrollmentRepository;


    @Override
    public List<EnrollmentDTO> getAllEnrollment() {
        List<Enrollment> enrollments = enrollmentRepository.findAll();
        return enrollments.stream()
                .map(EnrollmentDTO::new)
                .toList();
    }

    @Override
    public EnrollmentDTO getEnrollment(Long enrollmentId) {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(()-> new ResourceNotFoundException("Enrollment not found with ID: "+enrollmentId));
        return new EnrollmentDTO(enrollment);
    }

    @Override
    public EnrollmentDTO createEnrollment(Enrollment enrollment) {
        enrollment.setCreatedAt(LocalDateTime.now());
        Enrollment e = enrollmentRepository.save(enrollment);
        return new EnrollmentDTO(e);
    }

    @Override
    public EnrollmentDTO updateEnrollment(Enrollment enrollment) {
        Long enrollmentId = enrollment.getId();
        if(!enrollmentRepository.existsById(enrollmentId)){
            throw new ResourceNotFoundException("Enrollment not found with ID: "+enrollmentId);
        }
        Enrollment e = enrollmentRepository.saveAndFlush(enrollment);
        return new EnrollmentDTO(e);
    }

    @Override
    public void deleteEnrollment(Long enrollmentId) {

        if(!enrollmentRepository.existsById(enrollmentId)){
            throw new ResourceNotFoundException("Enrollment not found with ID: "+enrollmentId);
        }

        enrollmentRepository.deleteById(enrollmentId);
    }
}
