package com.example.CampusSync.attendance_token.dto;

import com.example.CampusSync.attendance_token.model.AttendanceToken;
import com.example.CampusSync.subject.dto.SubjectDTO;
import com.example.CampusSync.teacher.dto.TeacherDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceTokenInputDTO {
    private Long subjectId;
}
