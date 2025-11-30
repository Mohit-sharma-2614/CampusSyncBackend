package com.example.CampusSync.attendance.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@AllArgsConstructor
public class AttendanceInputDTO {

    private Long id;
    private Long studentId;
    private Long subjectId;
    private String status;

}
