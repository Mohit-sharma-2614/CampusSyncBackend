package com.example.CampusSync.attendance.service;

import com.example.CampusSync.attendance.dto.AttendanceDTO;
import com.example.CampusSync.attendance.dto.AttendanceInputDTO;
import com.example.CampusSync.attendance.model.Attendance;
import com.example.CampusSync.attendance.repository.AttendanceRepository;
import com.example.CampusSync.common.exceptions.ResourceNotFoundException;
import com.example.CampusSync.student.entity.Student;
import com.example.CampusSync.student.repository.StudentRepository;
import com.example.CampusSync.subject.model.Subject;
import com.example.CampusSync.subject.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.module.ResolutionException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@ComponentScan
@Service
public class AttendanceServiceImpl implements AttendanceService{
    @Autowired
    AttendanceRepository attendanceRepository;

    @Autowired
    SubjectRepository subjectRepository;

    @Autowired
    StudentRepository studentRepository;


    @Override
    public List<AttendanceDTO> getAllAttendance() {
        List<Attendance> attendance = attendanceRepository.findAll();
        return attendance.stream()
                .map(AttendanceDTO::new)
                .toList();
    }

    @Override
    public AttendanceDTO getAttendance(Long attendanceId) {
        Attendance a = attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new ResolutionException("Attendance not found with ID: "+attendanceId));
        return new AttendanceDTO(a);
    }

    @Transactional
    @Override
    public AttendanceDTO createAttendance(AttendanceInputDTO attendanceDTO) {
        attendanceDTO.setCreatedAt(LocalDateTime.now());
        attendanceDTO.setDate(LocalDate.now());

        // Validate studentId
        if (attendanceDTO.getStudentId() == null) {
            throw new IllegalArgumentException("Student ID cannot be null");
        }
        Student student = studentRepository.findById(attendanceDTO.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: " + attendanceDTO.getStudentId()));

        // Validate subjectId
        if (attendanceDTO.getSubjectId() == null) {
            throw new IllegalArgumentException("Subject ID cannot be null");
        }
        Subject subject = subjectRepository.findById(attendanceDTO.getSubjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found with ID: " + attendanceDTO.getSubjectId()));

        // Create and populate Attendance entity
        Attendance attendance = new Attendance();
        attendance.setStudent(student);
        attendance.setSubject(subject);
        attendance.setCreatedAt(attendanceDTO.getCreatedAt());
        attendance.setDate(attendanceDTO.getDate());
        attendance.setStatus(attendanceDTO.getStatus());

        // Save to database
        Attendance savedAttendance = attendanceRepository.save(attendance);
        return new AttendanceDTO(savedAttendance);
    }

    @Transactional
    @Override
    public AttendanceDTO updateAttendance(AttendanceInputDTO attendanceDTO) {
        Long attendanceId = attendanceDTO.getId();

        if (attendanceId == null) {
            throw new IllegalArgumentException("Attendance ID cannot be null");
        }

        // Check if attendance exists
        Attendance existingAttendance = attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new ResourceNotFoundException("Attendance not found with ID: " + attendanceId));

        // Validate and fetch student
        if (attendanceDTO.getStudentId() == null) {
            throw new IllegalArgumentException("Student ID cannot be null");
        }
        Student student = studentRepository.findById(attendanceDTO.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: " + attendanceDTO.getStudentId()));

        // Validate and fetch subject
        if (attendanceDTO.getSubjectId() == null) {
            throw new IllegalArgumentException("Subject ID cannot be null");
        }
        Subject subject = subjectRepository.findById(attendanceDTO.getSubjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found with ID: " + attendanceDTO.getSubjectId()));

        // Update fields
        existingAttendance.setStudent(student);
        existingAttendance.setSubject(subject);
        existingAttendance.setDate(attendanceDTO.getDate() != null ? attendanceDTO.getDate() : LocalDate.now());
        // Preserve createdAt unless explicitly provided
        if (attendanceDTO.getCreatedAt() != null) {
            existingAttendance.setCreatedAt(attendanceDTO.getCreatedAt());
        }

        // Save updated entity
        Attendance updatedAttendance = attendanceRepository.saveAndFlush(existingAttendance);
        return new AttendanceDTO(updatedAttendance);
    }

    @Override
    public void deleteAttendance(Long attendanceId) {
        if(!attendanceRepository.existsById(attendanceId)){
            throw new ResourceNotFoundException("Attendance not found with ID: "+attendanceId);
        }

        attendanceRepository.deleteById(attendanceId);
    }
}
