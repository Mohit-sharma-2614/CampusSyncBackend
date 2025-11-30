package com.example.CampusSync.department.service;

import java.util.List;

import com.example.CampusSync.department.dto.DepartmentDTO;
import com.example.CampusSync.department.model.Department;

public interface DepartmentService {
    List<DepartmentDTO> getAll();
    DepartmentDTO getDepartment(Long departmentId);
    DepartmentDTO createDepartment(Department department);
    DepartmentDTO updateDepartment(Department department);
    void deleteDepartment(Long departmentId);
}
