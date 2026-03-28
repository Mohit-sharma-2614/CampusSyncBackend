package com.example.CampusSync.student.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentRequestDTO {
    // User fields
    private String name;
    private String email;
    private String password;
    
    // Student fields
    private String rollNumber;
    private Long departmentId;
    private Integer year;
    private Integer semester;
    private String section;
}
