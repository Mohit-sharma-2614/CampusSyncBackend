package com.example.CampusSync.enrollment.dto;

import com.example.CampusSync.enrollment.model.Enrollment;
import com.example.CampusSync.student.dto.StudentDTO;
import com.example.CampusSync.courseofferings.dto.CourseOfferingsDetailsDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class EnrollmentDetailsDTO {
    private Long id;
    private StudentDTO student;
    private CourseOfferingsDetailsDTO courseOfferings;

    public EnrollmentDetailsDTO(Enrollment enrollment){
        this.id = enrollment.getId();
        if (enrollment.getStudent() != null) {
            this.student = new StudentDTO(enrollment.getStudent());
        }
        if (enrollment.getCourseOfferings() != null) {
            this.courseOfferings = new CourseOfferingsDetailsDTO(enrollment.getCourseOfferings());
        }
    }
}
