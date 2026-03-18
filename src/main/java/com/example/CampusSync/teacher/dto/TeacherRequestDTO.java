package com.example.CampusSync.teacher.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeacherRequestDTO {
    // User fields
    private String name;
    private String email;
    private String password;
    
    // Teacher fields
    private String employeeId;
    private String departmentId;
    private String designation;
}
