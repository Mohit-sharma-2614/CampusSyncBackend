package com.example.CampusSync.student.dto;

import com.example.CampusSync.student.entity.Student;
import lombok.Getter;
import lombok.Setter;

@Getter
public class StudentDTO {
    private Long id;
    private String name;
    private String student_uid;
    private String email;
    // Setter for jwt_token (optional, if you prefer setting it separately)
    @Setter
    private String jwt_token = " ";
    private int semester;
//    private String departmentName;

    // constructor
    public StudentDTO(Student student) {
        this.id = student.getId();
        this.name = student.getName();
        this.student_uid = student.getStudent_uid();
        this.email = student.getEmail();
        this.semester = student.getSemester();
    }
    // Constructor with JWT token
    public StudentDTO(Student student, String jwtToken) {
        this(student);
        this.jwt_token = jwtToken;
    }
}

