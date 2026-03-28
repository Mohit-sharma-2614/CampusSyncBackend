package com.example.CampusSync.courseofferings.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourseOfferingsInputDTO {
    private Long subjectId;
    private Long teacherId;
    private Integer academicYear;
    private Integer semester;
    private String section;
}
