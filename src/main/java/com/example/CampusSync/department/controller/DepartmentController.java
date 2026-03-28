package com.example.CampusSync.department.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.CampusSync.department.dto.DepartmentDTO;
import com.example.CampusSync.department.dto.DepartmentInputDTO;
import com.example.CampusSync.department.model.Department;
import com.example.CampusSync.department.service.DepartmentServiceImpl;


@RestController
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    DepartmentServiceImpl departmentService;

    @GetMapping("/all")
    public ResponseEntity<List<DepartmentDTO>> getAllDepartment() {
        List<DepartmentDTO> departments = departmentService.getAll();
        return ResponseEntity.ok(departments);
    }

    @GetMapping
    public ResponseEntity<DepartmentDTO> getDepartment(
            @RequestParam("departmentId") String departmentId) {
        try {
            DepartmentDTO dept = departmentService.getDepartment(Long.parseLong(departmentId));
            return ResponseEntity.ok(dept);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<DepartmentDTO> createDepartment(
            @RequestBody DepartmentInputDTO departmentInputDTO) {
        DepartmentDTO created = departmentService.createDepartment(departmentInputDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // TODO: change the request body to DepartmentInputDTO
    @PutMapping
    public ResponseEntity<DepartmentDTO> updateDepartment(
            @RequestBody Department department) {
        try {
            DepartmentDTO updated = departmentService.updateDepartment(department);
            return ResponseEntity.ok(updated);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteDepartment(
            @RequestParam("departmentId") String departmentId) {
        try {
            departmentService.deleteDepartment(Long.parseLong(departmentId));
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
