package com.example.CampusSync.enrollment.dto;

import com.example.CampusSync.enrollment.model.Enrollment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class EnrollmentDTO {
    private Long id;
    private Long studentId;
    private Long courseOfferingId;

    public EnrollmentDTO(Enrollment enrollment){
        this.id = enrollment.getId();
        if (enrollment.getStudent() != null) {
            this.studentId = enrollment.getStudent().getId();
        }
        if (enrollment.getCourseOfferings() != null) {
            this.courseOfferingId = enrollment.getCourseOfferings().getId();
        }
    }
}
