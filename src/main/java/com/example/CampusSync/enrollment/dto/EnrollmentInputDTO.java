package com.example.CampusSync.enrollment.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EnrollmentInputDTO {
    private Long studentId;          // Required
    private Long courseOfferingId;   // Required
    private LocalDateTime createdAt; // Optional: service auto-fills if null
}
