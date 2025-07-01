package com.example.CampusSync.teacher.dto;

// src/main/java/com/example/CampusSync/teacher/dto/TeacherRegistrationRequest.java
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeacherRegistrationRequest {
    private String name;
    private String email;
    private String password;
    @JsonProperty("department_id")
    private Long departmentId; // Use camelCase for Java fields, matches JSON "departmentId" or "department_id" if using @JsonProperty
    // If your JSON key is "department_id", you might want to add @JsonProperty("department_id")
    // private @JsonProperty("department_id") Long departmentId;
}