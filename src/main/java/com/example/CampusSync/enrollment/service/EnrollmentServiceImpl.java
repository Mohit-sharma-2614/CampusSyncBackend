package com.example.CampusSync.enrollment.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.CampusSync.common.exceptions.ResourceNotFoundException;
import com.example.CampusSync.enrollment.dto.EnrollmentDTO;
import com.example.CampusSync.enrollment.dto.EnrollmentDetailsDTO;
import com.example.CampusSync.enrollment.dto.EnrollmentInputDTO;
import com.example.CampusSync.enrollment.model.Enrollment;
import com.example.CampusSync.enrollment.repository.EnrollmentRepository;
import com.example.CampusSync.student.entity.Student;
import com.example.CampusSync.student.repository.StudentRepository;
import com.example.CampusSync.courseofferings.model.CourseOfferings;
import com.example.CampusSync.courseofferings.repository.CourseOfferingsRepository;

@ComponentScan
@Service
public class EnrollmentServiceImpl implements EnrollmentService{
    @Autowired
    EnrollmentRepository enrollmentRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    CourseOfferingsRepository courseOfferingsRepository;

    @Override
    public List<EnrollmentDTO> getAllEnrollment() {
        List<Enrollment> enrollments = enrollmentRepository.findAll();
        return enrollments.stream()
                .map(EnrollmentDTO::new)
                .toList();
    }

    @Override
    public List<EnrollmentDTO> findByStudentId(Long studentId) {
        List<Enrollment> enrollments = enrollmentRepository.findByStudentId(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Enrollment not found for StudentId: "+studentId));
        return enrollments.stream()
                .map(EnrollmentDTO::new)
                .toList();
    }

    @Override
    public List<EnrollmentDTO> findByCourseOfferingId(Long courseOfferingId) {
        List<Enrollment> enrollments = enrollmentRepository.findByCourseOfferingId(courseOfferingId)
                .orElseThrow(() -> new ResourceNotFoundException("Enrollment not found for courseOfferingId: "+courseOfferingId));
        return enrollments.stream()
                .map(EnrollmentDTO::new)
                .toList();
    }

    @Override
    public List<EnrollmentDTO> findByStudentIdAndCourseOfferingId(Long studentId, Long courseOfferingId) {
        List<Enrollment> enrollments = enrollmentRepository.findByStudentIdAndCourseOfferingId(studentId,courseOfferingId)
                .orElseThrow(() -> new ResourceNotFoundException("Enrollment not found for courseOfferingId: "+courseOfferingId+" and studentId: "+studentId));
        return enrollments.stream()
                .map(EnrollmentDTO::new)
                .toList();
    }

    @Override
    public List<EnrollmentDTO> findBySubjectId(Long subjectId) {
        List<Enrollment> enrollments = enrollmentRepository.findBySubjectId(subjectId)
                .orElseThrow(() -> new ResourceNotFoundException("Enrollment not found for subjectId: " + subjectId));
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
    public EnrollmentDetailsDTO getEnrollmentDetails(Long enrollmentId) {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(()-> new ResourceNotFoundException("Enrollment not found with ID: "+enrollmentId));
        return new EnrollmentDetailsDTO(enrollment);
    }

    @Transactional
    @Override
    public EnrollmentDTO createEnrollment(EnrollmentInputDTO enrollmentDTO) {

        if (enrollmentDTO.getStudentId() == null) {
            throw new IllegalArgumentException("Student ID cannot be null");
        }
        Student student = studentRepository.findById(enrollmentDTO.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: " + enrollmentDTO.getStudentId()));

        // Validate courseOffering
        if (enrollmentDTO.getCourseOfferingId() == null) {
            throw new IllegalArgumentException("CourseOffering ID cannot be null");
        }
        CourseOfferings courseOfferings = courseOfferingsRepository.findById(enrollmentDTO.getCourseOfferingId())
                .orElseThrow(() -> new ResourceNotFoundException("CourseOffering not found with ID: " + enrollmentDTO.getCourseOfferingId()));

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourseOfferings(courseOfferings);
        
        if (enrollmentDTO.getCreatedAt() != null) {
            enrollment.setCreatedAt(enrollmentDTO.getCreatedAt());
        } else {
            enrollment.setCreatedAt(LocalDateTime.now());
        }

        // Save the enrollment to the database
        Enrollment savedEnrollment = enrollmentRepository.save(enrollment);
        return new EnrollmentDTO(savedEnrollment);
    }

    @Transactional
    @Override
    public EnrollmentDTO updateEnrollment(Long enrollmentId, EnrollmentInputDTO enrollmentDTO) {
        if (enrollmentId == null) {
            throw new IllegalArgumentException("Enrollment ID cannot be null");
        }
        if (!enrollmentRepository.existsById(enrollmentId)) {
            throw new ResourceNotFoundException("Enrollment not found with ID: " + enrollmentId);
        }

        // Validate student
        if (enrollmentDTO.getStudentId() == null) {
            throw new IllegalArgumentException("Student ID cannot be null");
        }
        Student student = studentRepository.findById(enrollmentDTO.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: " + enrollmentDTO.getStudentId()));

        // Validate courseOffering
        if (enrollmentDTO.getCourseOfferingId() == null) {
            throw new IllegalArgumentException("CourseOffering ID cannot be null");
        }
        CourseOfferings courseOfferings = courseOfferingsRepository.findById(enrollmentDTO.getCourseOfferingId())
                .orElseThrow(() -> new ResourceNotFoundException("CourseOffering not found with ID: " + enrollmentDTO.getCourseOfferingId()));

        // Fetch existing entity to avoid overwriting createdAt
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Enrollment not found with ID: " + enrollmentId));
        
        enrollment.setStudent(student);
        enrollment.setCourseOfferings(courseOfferings);
        
        if (enrollmentDTO.getCreatedAt() != null) {
            enrollment.setCreatedAt(enrollmentDTO.getCreatedAt());
        }

        // Save updated enrollment
        Enrollment updatedEnrollment = enrollmentRepository.save(enrollment);
        return new EnrollmentDTO(updatedEnrollment);
    }

    @Transactional
    @Override
    public void deleteEnrollment(Long id) {
        if (!enrollmentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Enrollment not found with ID: " + id);
        }
        enrollmentRepository.deleteById(id);
    }
}
