package com.example.CampusSync.department.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.CampusSync.department.model.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department,Long> {
}
