package com.example.CampusSync.attendance.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import com.example.CampusSync.attendance.model.AttendanceStatus;

@Setter
@Getter
@AllArgsConstructor
public class AttendanceInputDTO {

    private Long id;
    private Long studentId;
    private Long subjectId;
    private AttendanceStatus status;

}
