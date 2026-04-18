package com.example.CampusSync.attendance.service;

import java.util.List;

import com.example.CampusSync.attendance.dto.AttendanceDTO;
import com.example.CampusSync.attendance.dto.AttendanceDetailsDTO;
import com.example.CampusSync.attendance.dto.AttendanceInputDTO;

public interface AttendanceService {
    List<AttendanceDTO> getAllAttendance();
    AttendanceDTO getAttendance(Long attendanceId);
    AttendanceDetailsDTO getAttendanceDetails(Long attendanceId);
    AttendanceDTO createAttendance(AttendanceInputDTO attendance);
    AttendanceDTO updateAttendance(Long attendanceId, AttendanceInputDTO attendance);
    void deleteAttendance(Long attendanceId);
    
    List<AttendanceDTO> getAttendanceByLectureSessionId(Long lectureSessionId);
    List<AttendanceDTO> getAttendanceByEnrollmentId(Long enrollmentId);
    List<AttendanceDTO> getAttendanceByLectureSessionAndEnrollmentId(Long lectureSessionId, Long enrollmentId);
    List<AttendanceDTO> createBulkAttendance(List<AttendanceInputDTO> attendanceInputs);
    List<AttendanceDTO> getAttendanceByStudentAndSubject(Long studentId, Long subjectId);
    List<AttendanceDTO> getAttendanceBySubjectId(Long subjectId);
}
