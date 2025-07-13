package com.example.CampusSync.attendance.service;

import com.example.CampusSync.attendance.dto.AttendanceDTO;
import com.example.CampusSync.attendance.dto.AttendanceInputDTO;
import com.example.CampusSync.attendance.model.Attendance;

import java.util.List;

public interface AttendanceService {
    List<AttendanceDTO> getAllAttendance();
    AttendanceDTO getAttendance(Long attendanceId);
    AttendanceDTO createAttendance(AttendanceInputDTO attendance);
    AttendanceDTO updateAttendance(AttendanceInputDTO attendance);
    void deleteAttendance(Long attendanceId);
    List<AttendanceDTO> getAttendanceBySubjectId(Long subjectId);

    List<AttendanceDTO> getAttendanceByStudentId(Long studentId);

    List<AttendanceDTO> getAttendanceBySubjectAndStudentId(Long subjectId, Long studentId);
}
