package com.example.CampusSync.student.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentRegisterDTO {
    // User fields
    private String name;
    private String email;
    private String password;
    
    // Student fields
    private String rollNumber;
    private String departmentId;
    private Integer year;
    private Integer semester;
    private String section;
}
