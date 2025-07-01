package com.example.CampusSync.department.controller;

import com.example.CampusSync.department.dto.DepartmentDTO;
import com.example.CampusSync.department.model.Department;
import com.example.CampusSync.department.service.DepartmentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;


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
            @RequestBody Department department) {
        DepartmentDTO created = departmentService.createDepartment(department);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

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
