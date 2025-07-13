package com.example.CampusSync.attendance.dto;

import com.example.CampusSync.student.dto.StudentDTO;
import com.example.CampusSync.subject.dto.SubjectDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Setter
@Getter
@AllArgsConstructor
public class AttendanceInputDTO {

    private Long id;
    private Long studentId;
    private Long subjectId;
    private String status;

}
