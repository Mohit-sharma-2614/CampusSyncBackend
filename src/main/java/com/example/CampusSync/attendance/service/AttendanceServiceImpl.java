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
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;


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
    public List<AttendanceDTO> getAttendanceBySubjectId(Long subjectId) {
        // Verify subject exists
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found with ID: " + subjectId));

        // Fetch and map
        List<Attendance> attendances = attendanceRepository.findBySubjectId(subjectId);
        return attendances.stream().map(AttendanceDTO::new).toList();
    }

    @Override
    public List<AttendanceDTO> getAttendanceByStudentId(Long studentId) {
        // Verify student exists
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: " + studentId));

        // Fetch and map
        List<Attendance> attendances = attendanceRepository.findByStudentId(studentId);
        return attendances.stream().map(AttendanceDTO::new).toList();
    }

    @Override
    public List<AttendanceDTO> getAttendanceBySubjectAndStudentId(Long subjectId, Long studentId) {
        // Verify subject and student exist
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found with ID: " + subjectId));

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: " + studentId));

        // Fetch and map
        List<Attendance> attendances = attendanceRepository.findBySubjectIdAndStudentId(subjectId, studentId);
        return attendances.stream().map(AttendanceDTO::new).toList();
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
        attendance.setCreatedAt(LocalDateTime.now());
        attendance.setDate(LocalDate.now());
        attendance.setStatus(attendanceDTO.getStatus());

        // Save to database
        Attendance savedAttendance = attendanceRepository.save(attendance);
        return new AttendanceDTO(savedAttendance);
    }

    @Override
    @Transactional
    public List<AttendanceDTO> createBulkAttendance(List<AttendanceInputDTO> attendanceInputs) {
        if (attendanceInputs == null || attendanceInputs.isEmpty()) {
            throw new IllegalArgumentException("Attendance input list cannot be null or empty");
        }

        List<Attendance> attendances = attendanceInputs.stream().map(input -> {
            // Validate student
            Student student = studentRepository.findById(input.getStudentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + input.getStudentId()));

            // Validate subject
            Subject subject = subjectRepository.findById(input.getSubjectId())
                    .orElseThrow(() -> new ResourceNotFoundException("Subject not found with id: " + input.getSubjectId()));

            // Validate status
            if (input.getStatus() == null || !List.of("PRESENT", "ABSENT").contains(input.getStatus().toUpperCase())) {
                throw new IllegalArgumentException("Invalid status: " + input.getStatus());
            }

            // Create Attendance entity
            Attendance attendance = new Attendance();
            attendance.setStudent(student);
            attendance.setSubject(subject);
            attendance.setDate(LocalDate.now()); // Default to today; adjust if input includes date
            attendance.setStatus(input.getStatus().toUpperCase());

            return attendance;
        }).collect(Collectors.toList());

        // Save all attendance records
        List<Attendance> savedAttendances = attendanceRepository.saveAll(attendances);
        return savedAttendances.stream()
                .map(AttendanceDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<AttendanceDTO> getAttendanceBySubjectAndDate(Long subjectId, String date) {
        try {
            LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);

            // Validate subject
            subjectRepository.findById(subjectId)
                    .orElseThrow(() -> new ResourceNotFoundException("Subject not found with id: " + subjectId));

            List<Attendance> attendances = attendanceRepository.findBySubjectIdAndDate(subjectId, localDate)
                    .orElseThrow(() -> new ResourceNotFoundException("No attendance records found for subjectId: " + subjectId + " on date: " + date));

            return attendances.stream()
                    .map(AttendanceDTO::new)
                    .collect(Collectors.toList());
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format: " + date, e);
        }
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
        // Preserve createdAt unless explicitly provided

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
