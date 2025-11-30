package com.example.CampusSync.teacher.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeacherRegistrationRequest {
    private String name;
    private String email;
    private String password;
    private Long departmentId; // Use camelCase for Java fields, matches JSON "departmentId" or "department_id" if using @JsonProperty
    // If your JSON key is "department_id", you might want to add @JsonProperty("department_id")
    // private @JsonProperty("department_id") Long departmentId;
}