package com.example.CampusSync.courseofferings.dto;

import com.example.CampusSync.courseofferings.model.CourseOfferings;
import com.example.CampusSync.subject.dto.SubjectDetailsDTO;
import com.example.CampusSync.teacher.dto.TeacherDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CourseOfferingsDetailsDTO {
    private Long id;
    private SubjectDetailsDTO subject;
    private TeacherDTO teacher;
    private Integer academicYear;
    private Integer semester;
    private String section;

    public CourseOfferingsDetailsDTO(CourseOfferings courseOfferings) {
        if (courseOfferings != null) {
            this.id = courseOfferings.getId();
            if (courseOfferings.getSubject() != null) {
                this.subject = new SubjectDetailsDTO(courseOfferings.getSubject());
            }
            if (courseOfferings.getTeacher() != null) {
                this.teacher = new TeacherDTO(courseOfferings.getTeacher());
            }
            this.academicYear = courseOfferings.getAcademicYear();
            this.semester = courseOfferings.getSemester();
            this.section = courseOfferings.getSection();
        }
    }
}
