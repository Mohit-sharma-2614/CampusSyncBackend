package com.example.CampusSync.subject.dto;

import com.example.CampusSync.department.dto.DepartmentDTO;
import com.example.CampusSync.subject.model.Subject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SubjectDetailsDTO {

    private Long id;
    private String name;
    private String code;
    private Integer credits;
    private DepartmentDTO department;

    public SubjectDetailsDTO(Subject subject) {
        this.id = subject.getId();
        this.name = subject.getName();
        this.code = subject.getCode();
        this.credits = subject.getCredits();
        if (subject.getDepartment() != null) {
            this.department = new DepartmentDTO(subject.getDepartment());
        }
    }
}
