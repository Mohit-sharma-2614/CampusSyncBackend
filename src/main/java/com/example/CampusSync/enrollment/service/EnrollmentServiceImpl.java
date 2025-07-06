package com.example.CampusSync.enrollment.service;

import com.example.CampusSync.enrollment.dto.EnrollmentDTO;
import com.example.CampusSync.enrollment.dto.EnrollmentInputDTO;
import com.example.CampusSync.enrollment.model.Enrollment;
import com.example.CampusSync.enrollment.repository.EnrollmentRepository;
import com.example.CampusSync.common.exceptions.ResourceAlreadyExistException;
import com.example.CampusSync.common.exceptions.ResourceNotFoundException;
import com.example.CampusSync.student.entity.Student;
import com.example.CampusSync.student.repository.StudentRepository;
import com.example.CampusSync.subject.model.Subject;
import com.example.CampusSync.subject.repository.SubjectRepository;
import com.example.CampusSync.teacher.dto.TeacherDTO;
import com.example.CampusSync.teacher.model.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@ComponentScan
@Service
public class EnrollmentServiceImpl implements EnrollmentService{
    @Autowired
    EnrollmentRepository enrollmentRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    SubjectRepository subjectRepository;

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

    @Transactional
    @Override
    public EnrollmentDTO createEnrollment(EnrollmentInputDTO enrollmentDTO) {
        enrollmentDTO.setCreatedAt(LocalDateTime.now());

        if (enrollmentDTO.getStudentId() == null) {
            throw new IllegalArgumentException("Student ID cannot be null");
        }
        Student student = studentRepository.findById(enrollmentDTO.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: " + enrollmentDTO.getStudentId()));

        // Validate subject
        if (enrollmentDTO.getSubjectId() == null) {
            throw new IllegalArgumentException("Subject ID cannot be null");
        }
        Subject subject = subjectRepository.findById(enrollmentDTO.getSubjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found with ID: " + enrollmentDTO.getSubjectId()));

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setSubject(subject);

        // Save the enrollment to the database
        Enrollment savedEnrollment = enrollmentRepository.save(enrollment);
        return new EnrollmentDTO(savedEnrollment);
    }

    @Transactional
    @Override
    public EnrollmentDTO updateEnrollment(EnrollmentInputDTO enrollmentDTO) {
        if (enrollmentDTO.getId() == null) {
            throw new IllegalArgumentException("Enrollment ID cannot be null");
        }
        if (!enrollmentRepository.existsById(enrollmentDTO.getId())) {
            throw new ResourceNotFoundException("Enrollment not found with ID: " + enrollmentDTO.getId());
        }

        // Validate student
        if (enrollmentDTO.getStudentId() == null) {
            throw new IllegalArgumentException("Student ID cannot be null");
        }
        Student student = studentRepository.findById(enrollmentDTO.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: " + enrollmentDTO.getStudentId()));

        // Validate subject
        if (enrollmentDTO.getSubjectId() == null) {
            throw new IllegalArgumentException("Subject ID cannot be null");
        }
        Subject subject = subjectRepository.findById(enrollmentDTO.getSubjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found with ID: " + enrollmentDTO.getSubjectId()));

        // Map DTO to entity
        Enrollment enrollment = new Enrollment();
        enrollment.setId(enrollmentDTO.getId());
        enrollment.setStudent(student);
        enrollment.setSubject(subject);

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
