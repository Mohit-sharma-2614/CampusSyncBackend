package com.example.CampusSync.teacher.dto;

import com.example.CampusSync.teacher.model.Teacher;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter 
@Setter 
@NoArgsConstructor 
@AllArgsConstructor 
public class TeacherResponseDTO {

    private Long id;
    private String name;
    private String email;
    private String employeeId;
    private String departmentId;
    private String designation;
    
    private String jwtToken;
    private String refreshToken;

    // Constructor to convert from Teacher entity to TeacherResponseDTO
    public TeacherResponseDTO(Teacher teacher) {
        this.id = teacher.getId();
        if (teacher.getUser() != null) {
            this.name = teacher.getUser().getName();
            this.email = teacher.getUser().getEmail();
        }
        this.employeeId = teacher.getEmployeeId();
        this.departmentId = teacher.getDepartmentId();
        this.designation = teacher.getDesignation();
    }
    
    public TeacherResponseDTO(Teacher teacher, String jwtToken, String refreshToken) {
        this(teacher);
        this.jwtToken = jwtToken;
        this.refreshToken = refreshToken;
    }
}
