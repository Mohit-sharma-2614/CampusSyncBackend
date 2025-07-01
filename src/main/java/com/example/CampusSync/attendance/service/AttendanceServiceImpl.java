package com.example.CampusSync.attendance.service;

import com.example.CampusSync.attendance.dto.AttendanceDTO;
import com.example.CampusSync.attendance.model.Attendance;
import com.example.CampusSync.attendance.repository.AttendanceRepository;
import com.example.CampusSync.common.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@ComponentScan
@Service
public class AttendanceServiceImpl implements AttendanceService{
    @Autowired
    AttendanceRepository attendanceRepository;


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

    @Override
    public AttendanceDTO createAttendance(Attendance attendance) {
        attendance.setCreatedAt(LocalDateTime.now());
        attendance.setDate(LocalDate.now());
        Attendance a = attendanceRepository.save(attendance);
        return new AttendanceDTO(a);
    }

    @Override
    public AttendanceDTO updateAttendance(Attendance attendance) {
        Long attendanceId = attendance.getId();

        if(!attendanceRepository.existsById(attendanceId)){
            throw new ResourceNotFoundException("Attendance not found with ID: "+attendanceId);
        }
        Attendance a = attendanceRepository.saveAndFlush(attendance);
        return new AttendanceDTO(a);
    }

    @Override
    public void deleteAttendance(Long attendanceId) {
        if(!attendanceRepository.existsById(attendanceId)){
            throw new ResourceNotFoundException("Attendance not found with ID: "+attendanceId);
        }

        attendanceRepository.deleteById(attendanceId);
    }
}
