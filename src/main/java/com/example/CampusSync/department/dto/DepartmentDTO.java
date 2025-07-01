package com.example.CampusSync.department.dto;

import com.example.CampusSync.department.model.Department;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class DepartmentDTO {
    private Long id;
    private String name;

    public DepartmentDTO(Department department){
        this.id = department.getId();
        this.name = department.getName();
    }

}
