package com.example.CampusSync.courseofferings.dto;

import com.example.CampusSync.courseofferings.model.CourseOfferings;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CourseOfferingsDTO {
    private Long id;
    private Long subjectId;
    private Long teacherId;
    private Integer academicYear;
    private Integer semester;
    private String section;

    public CourseOfferingsDTO(CourseOfferings courseOfferings) {
        if (courseOfferings != null) {
            this.id = courseOfferings.getId();
            if (courseOfferings.getSubject() != null) {
                this.subjectId = courseOfferings.getSubject().getId();
            }
            if (courseOfferings.getTeacher() != null) {
                this.teacherId = courseOfferings.getTeacher().getId();
            }
            this.academicYear = courseOfferings.getAcademicYear();
            this.semester = courseOfferings.getSemester();
            this.section = courseOfferings.getSection();
        }
    }
}
