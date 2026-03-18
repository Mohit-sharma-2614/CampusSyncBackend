package com.example.CampusSync.student.dto;

import com.example.CampusSync.student.entity.Student;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentResponseDTO {
    private Long id;
    private String name;
    private String email;
    private String rollNumber;
    private String departmentId;
    private Integer year;
    private Integer semester;
    private String section;
    private String jwtToken;
    private String refreshToken;

    public StudentResponseDTO() {}

    // Constructor to convert Student entity to DTO
    public StudentResponseDTO(Student student) {
        this.id = student.getId();
        if (student.getUser() != null) {
            this.name = student.getUser().getName();
            this.email = student.getUser().getEmail();
        }
        this.rollNumber = student.getRollNumber();
        this.departmentId = student.getDepartmentId();
        this.year = student.getYear();
        this.semester = student.getSemester();
        this.section = student.getSection();
    }
    
    public StudentResponseDTO(Student student, String jwtToken, String refreshToken) {
        this(student);
        this.jwtToken = jwtToken;
        this.refreshToken = refreshToken;
    }
}
