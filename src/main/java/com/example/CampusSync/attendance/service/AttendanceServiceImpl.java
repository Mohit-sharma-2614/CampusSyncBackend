package com.example.CampusSync.attendance.service;

import java.lang.module.ResolutionException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.CampusSync.attendance.dto.AttendanceDTO;
import com.example.CampusSync.attendance.dto.AttendanceDetailsDTO;
import com.example.CampusSync.attendance.dto.AttendanceInputDTO;
import com.example.CampusSync.attendance.model.Attendance;
import com.example.CampusSync.attendance.repository.AttendanceRepository;
import com.example.CampusSync.attendance_token.model.AttendanceToken;
import com.example.CampusSync.attendance_token.repository.AttendanceTokenRepository;
import com.example.CampusSync.common.exceptions.ResourceNotFoundException;
import com.example.CampusSync.enrollment.model.Enrollment;
import com.example.CampusSync.enrollment.repository.EnrollmentRepository;
import com.example.CampusSync.lecturesessions.model.LectureSessions;
import com.example.CampusSync.lecturesessions.repository.LectureSessionsRepository;

@ComponentScan
@Service
public class AttendanceServiceImpl implements AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private LectureSessionsRepository lectureSessionsRepository;

    @Autowired
    private AttendanceTokenRepository attendanceTokenRepository;

    @Override
    public List<AttendanceDTO> getAllAttendance() {
        List<Attendance> attendance = attendanceRepository.findAll();
        return attendance.stream()
                .map(AttendanceDTO::new)
                .toList();
    }

    @Override
    public List<AttendanceDTO> getAttendanceByLectureSessionId(Long lectureSessionId) {
        // LectureSessions session = lectureSessionsRepository.findById(lectureSessionId)
        //         .orElseThrow(() -> new ResourceNotFoundException("Lecture Session not found with ID: " + lectureSessionId));

        List<Attendance> attendances = attendanceRepository.findByLectureSessionId(lectureSessionId);
        return attendances.stream().map(AttendanceDTO::new).toList();
    }

    @Override
    public List<AttendanceDTO> getAttendanceByEnrollmentId(Long enrollmentId) {
        // Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
        //         .orElseThrow(() -> new ResourceNotFoundException("Enrollment not found with ID: " + enrollmentId));

        List<Attendance> attendances = attendanceRepository.findByEnrollmentId(enrollmentId);
        return attendances.stream().map(AttendanceDTO::new).toList();
    }

    @Override
    public List<AttendanceDTO> getAttendanceByLectureSessionAndEnrollmentId(Long lectureSessionId, Long enrollmentId) {
        lectureSessionsRepository.findById(lectureSessionId)
                .orElseThrow(() -> new ResourceNotFoundException("Lecture Session not found with ID: " + lectureSessionId));

        enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Enrollment not found with ID: " + enrollmentId));

        List<Attendance> attendances = attendanceRepository.findByLectureSessionIdAndEnrollmentId(lectureSessionId, enrollmentId);
        return attendances.stream().map(AttendanceDTO::new).toList();
    }

    @Override
    public AttendanceDTO getAttendance(Long attendanceId) {
        Attendance a = attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new ResolutionException("Attendance not found with ID: " + attendanceId));
        return new AttendanceDTO(a);
    }

    @Override
    public AttendanceDetailsDTO getAttendanceDetails(Long attendanceId) {
        Attendance a = attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new ResourceNotFoundException("Attendance not found with ID: " + attendanceId));
        return new AttendanceDetailsDTO(a);
    }

    @Transactional
    @Override
    public AttendanceDTO createAttendance(AttendanceInputDTO attendanceDTO) {
        if (attendanceDTO.getEnrollmentId() == null) {
            throw new IllegalArgumentException("Enrollment ID cannot be null");
        }
        Enrollment enrollment = enrollmentRepository.findById(attendanceDTO.getEnrollmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Enrollment not found with ID: " + attendanceDTO.getEnrollmentId()));

        if (attendanceDTO.getLectureSessionId() == null) {
            throw new IllegalArgumentException("Lecture Session ID cannot be null");
        }
        LectureSessions session = lectureSessionsRepository.findById(attendanceDTO.getLectureSessionId())
                .orElseThrow(() -> new ResourceNotFoundException("Lecture Session not found with ID: " + attendanceDTO.getLectureSessionId()));

        if (attendanceDTO.getTokenId() == null) {
            throw new IllegalArgumentException("Token ID cannot be null");
        }
        AttendanceToken token = attendanceTokenRepository.findById(attendanceDTO.getTokenId())
                .orElseThrow(() -> new ResourceNotFoundException("Token not found with ID: " + attendanceDTO.getTokenId()));

        Attendance attendance = new Attendance();
        attendance.setEnrollment(enrollment);
        attendance.setLectureSessions(session);
        attendance.setToke(token);
        attendance.setStatus(attendanceDTO.getStatus());

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
            Enrollment enrollment = enrollmentRepository.findById(input.getEnrollmentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Enrollment not found with id: " + input.getEnrollmentId()));

            LectureSessions session = lectureSessionsRepository.findById(input.getLectureSessionId())
                    .orElseThrow(() -> new ResourceNotFoundException("Lecture Session not found with id: " + input.getLectureSessionId()));

            AttendanceToken token = attendanceTokenRepository.findById(input.getTokenId())
                    .orElseThrow(() -> new ResourceNotFoundException("Token not found with id: " + input.getTokenId()));

            if (input.getStatus() == null) {
                throw new IllegalArgumentException("Invalid status: " + input.getStatus());
            }

            Attendance attendance = new Attendance();
            attendance.setEnrollment(enrollment);
            attendance.setLectureSessions(session);
            attendance.setToke(token);
            attendance.setStatus(input.getStatus());

            return attendance;
        }).collect(Collectors.toList());

        List<Attendance> savedAttendances = attendanceRepository.saveAll(attendances);
        return savedAttendances.stream()
                .map(AttendanceDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public AttendanceDTO updateAttendance(Long attendanceId, AttendanceInputDTO attendanceDTO) {
        if (attendanceId == null) {
            throw new IllegalArgumentException("Attendance ID cannot be null");
        }

        Attendance existingAttendance = attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new ResourceNotFoundException("Attendance not found with ID: " + attendanceId));

        if (attendanceDTO.getEnrollmentId() == null) {
            throw new IllegalArgumentException("Enrollment ID cannot be null");
        }
        Enrollment enrollment = enrollmentRepository.findById(attendanceDTO.getEnrollmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Enrollment not found with ID: " + attendanceDTO.getEnrollmentId()));

        if (attendanceDTO.getLectureSessionId() == null) {
            throw new IllegalArgumentException("Lecture Session ID cannot be null");
        }
        LectureSessions session = lectureSessionsRepository.findById(attendanceDTO.getLectureSessionId())
                .orElseThrow(() -> new ResourceNotFoundException("Lecture Session not found with ID: " + attendanceDTO.getLectureSessionId()));

        if (attendanceDTO.getTokenId() == null) {
            throw new IllegalArgumentException("Token ID cannot be null");
        }
        AttendanceToken token = attendanceTokenRepository.findById(attendanceDTO.getTokenId())
                .orElseThrow(() -> new ResourceNotFoundException("Token not found with ID: " + attendanceDTO.getTokenId()));

        existingAttendance.setEnrollment(enrollment);
        existingAttendance.setLectureSessions(session);
        existingAttendance.setToke(token);
        existingAttendance.setStatus(attendanceDTO.getStatus());

        Attendance updatedAttendance = attendanceRepository.saveAndFlush(existingAttendance);
        return new AttendanceDTO(updatedAttendance);
    }

    @Override
    public void deleteAttendance(Long attendanceId) {
        if(!attendanceRepository.existsById(attendanceId)){
            throw new ResourceNotFoundException("Attendance not found with ID: " + attendanceId);
        }

        attendanceRepository.deleteById(attendanceId);
    }
}
