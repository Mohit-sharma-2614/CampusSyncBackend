package com.example.CampusSync.attendance.service;

import com.example.CampusSync.attendance.dto.AttendanceDTO;
import com.example.CampusSync.attendance.model.Attendance;

import java.util.List;

public interface AttendanceService {
    List<AttendanceDTO> getAllAttendance();
    AttendanceDTO getAttendance(Long attendanceId);
    AttendanceDTO createAttendance(Attendance attendance);
    AttendanceDTO updateAttendance(Attendance attendance);
    void deleteAttendance(Long attendanceId);
}
