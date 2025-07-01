package com.example.CampusSync.teacher.dto;

import com.example.CampusSync.student.entity.Student;
import com.example.CampusSync.teacher.model.Teacher;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Getter // Lombok annotation for getters
@Setter // Lombok annotation for setters (useful if you accept TeacherDTO in requests)
@NoArgsConstructor // Lombok annotation for a no-argument constructor
@AllArgsConstructor // Lombok annotation for a constructor with all fields
public class TeacherDTO {

    private Long id;
    private String name;
    private String email;
    @Setter
    private String jwtToken;
    // Do NOT include 'password' for security reasons in a DTO returned to the client
    private String departmentName; // Just the department name, not the whole Department object

    // Constructor to convert from Teacher entity to TeacherDTO
    public TeacherDTO(Teacher teacher) {
        this.id = teacher.getId();
        this.name = teacher.getName();
        this.email = teacher.getEmail();
        // Null check for department in case it's optional or not loaded
        this.departmentName = (teacher.getDepartment() != null) ? teacher.getDepartment().getName() : null;
    }
    public TeacherDTO(Teacher teacher, String jwtToken) {
        this(teacher);
        this.jwtToken = jwtToken;
    }
}
