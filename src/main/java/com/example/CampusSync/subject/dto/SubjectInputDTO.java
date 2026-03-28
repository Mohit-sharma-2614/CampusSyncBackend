package com.example.CampusSync.subject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SubjectInputDTO {
    private String name;
    private String code;
    private Integer credits;
    private Long departmentId;
}
