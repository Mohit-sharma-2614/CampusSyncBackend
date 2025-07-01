package com.example.CampusSync.department.service;

import com.example.CampusSync.department.dto.DepartmentDTO;
import com.example.CampusSync.department.model.Department;
import com.example.CampusSync.department.repository.DepartmentRepository;
import com.example.CampusSync.common.exceptions.ResourceAlreadyExistException;
import com.example.CampusSync.common.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public DepartmentDTO createDepartment(Department department) {
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
