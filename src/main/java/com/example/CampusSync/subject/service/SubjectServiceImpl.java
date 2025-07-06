package com.example.CampusSync.subject.service;

import com.example.CampusSync.common.exceptions.ResourceAlreadyExistException;
import com.example.CampusSync.common.exceptions.ResourceNotFoundException;
import com.example.CampusSync.department.model.Department;
import com.example.CampusSync.department.repository.DepartmentRepository;
import com.example.CampusSync.subject.dto.SubjectDTO;
import com.example.CampusSync.subject.model.Subject;
import com.example.CampusSync.subject.repository.SubjectRepository;
import com.example.CampusSync.teacher.model.Teacher;
import com.example.CampusSync.teacher.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@ComponentScan
public class SubjectServiceImpl implements SubjectService{

    @Autowired
    SubjectRepository subjectRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    TeacherRepository teacherRepository;


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
    public SubjectDTO createSubject(Subject subject) {

        // Validate department
        if (subject.getDepartment() == null || subject.getDepartment().getId() == null) {
            throw new IllegalArgumentException("Department ID cannot be null");
        }
        Department department = departmentRepository.findById(subject.getDepartment().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with ID: " + subject.getDepartment().getId()));
        subject.setDepartment(department);

        // Validate teacher
        if (subject.getTeacher() == null || subject.getTeacher().getId() == null) {
            throw new IllegalArgumentException("Teacher ID cannot be null");
        }
        Teacher teacher = teacherRepository.findById(subject.getTeacher().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found with ID: " + subject.getTeacher().getId()));
        subject.setTeacher(teacher);

        // Set created_at
        subject.setCreatedAt(LocalDateTime.now());

        // Save subject
        Subject savedSubject = subjectRepository.save(subject);
        return new SubjectDTO(savedSubject);
    }

    @Override
    public SubjectDTO updateSubject(Subject subject) {

        Long subjectId = subject.getId();
        if (!subjectRepository.existsById(subjectId)) {
            throw new ResourceNotFoundException("Subject not found with ID: " + subjectId);
        }

        // Validate department
        if (subject.getDepartment() == null || subject.getDepartment().getId() == null) {
            throw new IllegalArgumentException("Department ID cannot be null");
        }
        Department department = departmentRepository.findById(subject.getDepartment().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with ID: " + subject.getDepartment().getId()));
        subject.setDepartment(department);

        // Validate teacher
        if (subject.getTeacher() == null || subject.getTeacher().getId() == null) {
            throw new IllegalArgumentException("Teacher ID cannot be null");
        }
        Teacher teacher = teacherRepository.findById(subject.getTeacher().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found with ID: " + subject.getTeacher().getId()));
        subject.setTeacher(teacher);

        // Save updated subject
        Subject updatedSubject = subjectRepository.saveAndFlush(subject);
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