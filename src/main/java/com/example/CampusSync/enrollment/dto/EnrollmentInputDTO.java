package com.example.CampusSync.enrollment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class EnrollmentInputDTO {
    private Long id;
    private Long studentId;
    private Long subjectId;
    private LocalDateTime createdAt;
}
