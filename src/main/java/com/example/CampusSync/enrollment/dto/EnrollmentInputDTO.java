package com.example.CampusSync.enrollment.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EnrollmentInputDTO {
    private Long id;
    private Long studentId;
    private Long subjectId;
    private LocalDateTime createdAt;
}
