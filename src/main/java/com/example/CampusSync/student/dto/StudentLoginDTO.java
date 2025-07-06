package com.example.CampusSync.student.dto;

import com.example.CampusSync.student.entity.Student;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StudentLoginDTO {
    private String email;
    private String password;
}
