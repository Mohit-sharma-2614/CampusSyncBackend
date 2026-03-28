package com.example.CampusSync.subject.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import com.example.CampusSync.common.exceptions.ResourceNotFoundException;
import com.example.CampusSync.department.model.Department;
import com.example.CampusSync.department.repository.DepartmentRepository;
import com.example.CampusSync.subject.dto.SubjectDTO;
import com.example.CampusSync.subject.dto.SubjectDetailsDTO;
import com.example.CampusSync.subject.dto.SubjectInputDTO;
import com.example.CampusSync.subject.model.Subject;
import com.example.CampusSync.subject.repository.SubjectRepository;

@Service
@ComponentScan
public class SubjectServiceImpl implements SubjectService{

    @Autowired
    SubjectRepository subjectRepository;

    @Autowired
    DepartmentRepository departmentRepository;


    @Override
    public List<SubjectDTO> getAllSubjects() {
        List<Subject> subject = subjectRepository.findAll();
        return subject.stream()
                .map(SubjectDTO::new)
                .toList();
    }

    @Override
    public SubjectDTO getSubject(Long subjectId) {
        Subject subject = subjectRepository.findById(subjectId).orElseThrow(()->new ResourceNotFoundException("Subject not found with ID: "+subjectId));
        return new SubjectDTO(subject);
    }

    @Override
    public SubjectDetailsDTO getSubjectDetails(Long subjectId) {
        Subject subject = subjectRepository.findById(subjectId).orElseThrow(()->new ResourceNotFoundException("Subject not found with ID: "+subjectId));
        return new SubjectDetailsDTO(subject);
    }

    @Override
    public SubjectDTO createSubject(SubjectInputDTO subjectDTO) {

        // Validate department
        if (subjectDTO.getDepartmentId() == null) {
            throw new IllegalArgumentException("Department ID cannot be null");
        }
        Department department = departmentRepository.findById(subjectDTO.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with ID: " + subjectDTO.getDepartmentId()));

        Subject subject = new Subject();
        subject.setName(subjectDTO.getName());
        subject.setCode(subjectDTO.getCode());
        subject.setCredits(subjectDTO.getCredits());
        subject.setDepartment(department);

        // Set created_at
        subject.setCreatedAt(java.sql.Timestamp.valueOf(LocalDateTime.now()));

        // Save subject
        Subject savedSubject = subjectRepository.save(subject);
        return new SubjectDTO(savedSubject);
    }

    @Override
    public SubjectDTO updateSubject(Long subjectId, SubjectInputDTO subjectDTO) {

        if (subjectId == null) {
            throw new IllegalArgumentException("Subject ID cannot be null for update");
        }
        
        Subject existingSubject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found with ID: " + subjectId));

        // Validate department
        if (subjectDTO.getDepartmentId() == null) {
            throw new IllegalArgumentException("Department ID cannot be null");
        }
        Department department = departmentRepository.findById(subjectDTO.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with ID: " + subjectDTO.getDepartmentId()));
        existingSubject.setDepartment(department);

        existingSubject.setName(subjectDTO.getName());
        existingSubject.setCode(subjectDTO.getCode());
        existingSubject.setCredits(subjectDTO.getCredits());

        // Save updated subject
        Subject updatedSubject = subjectRepository.saveAndFlush(existingSubject);
        return new SubjectDTO(updatedSubject);
    }

    @Override
    public void deleteSubject(Long subjectId) {


        if(!subjectRepository.existsById(subjectId)){
            throw new ResourceNotFoundException("Subject not found with ID: "+subjectId);
        }

        subjectRepository.deleteById(subjectId);
    }
}