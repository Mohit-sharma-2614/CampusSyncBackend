package com.example.CampusSync.department.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.CampusSync.common.exceptions.ResourceNotFoundException;
import com.example.CampusSync.department.dto.DepartmentDTO;
import com.example.CampusSync.department.dto.DepartmentInputDTO;
import com.example.CampusSync.department.model.Department;
import com.example.CampusSync.department.repository.DepartmentRepository;

@Service
public class DepartmentServiceImpl implements DepartmentService{
    @Autowired
    DepartmentRepository departmentRepository;


    @Override
    public List<DepartmentDTO> getAll() {
        List<Department> departments = departmentRepository.findAll();
        return departments.stream()
                .map(DepartmentDTO::new)
                .toList();
    }

    @Override
    public DepartmentDTO getDepartment(Long departmentId) {
        Department d = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with ID: "+departmentId));
        return new DepartmentDTO(d);
    }

    @Override
    public DepartmentDTO createDepartment(DepartmentInputDTO departmentInputDTO) {
        Department department = new Department();
        department.setName(departmentInputDTO.getName());
        department.setCode(departmentInputDTO.getCode());
        department.setCreatedAt(new java.sql.Timestamp(System.currentTimeMillis()));
        Department d = departmentRepository.save(department);
        return new DepartmentDTO(d);
    }

    @Override
    public DepartmentDTO updateDepartment(Department department) {
        Long departmentId = department.getId();

        if(!departmentRepository.existsById(departmentId)){
            throw new ResourceNotFoundException("Department not found with ID: "+departmentId);
        }
        Department d = departmentRepository.saveAndFlush(department);
        return new DepartmentDTO(d);
    }

    @Override
    public void deleteDepartment(Long departmentId) {

        if(!departmentRepository.existsById(departmentId)){
            throw new ResourceNotFoundException("Department not found with ID: "+departmentId);
        }

        departmentRepository.deleteById(departmentId);
    }
}
