package com.example.CampusSync.student.service;

import com.example.CampusSync.common.exceptions.BadCredentialsException;
import com.example.CampusSync.common.exceptions.ResourceAlreadyExistException;
import com.example.CampusSync.common.exceptions.ResourceNotFoundException;
import com.example.CampusSync.common.security.JWTService;
import com.example.CampusSync.department.model.Department;
import com.example.CampusSync.department.repository.DepartmentRepository;
import com.example.CampusSync.student.dto.StudentDTO;
import com.example.CampusSync.student.dto.StudentLoginDTO;
import com.example.CampusSync.student.entity.Student;
import com.example.CampusSync.student.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@ComponentScan
@Service
public class StudentServiceImpl implements StudentService {
    @Autowired(required = true)
    StudentRepository studentRepository;

    @Autowired
    AuthenticationManager authManager;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JWTService jwtService;

    @Autowired
    DepartmentRepository departmentRepository;

    @Override
    public List<StudentDTO> getAllStudents() {
        List<Student> s = studentRepository.findAll();
        return s.stream()
                .map(StudentDTO::new)
                .toList();
    }

    @Override
    public StudentDTO getStudent(Long studentId) {
        Student s = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: "+studentId));
        return new StudentDTO(s);
    }

    @Override
    public StudentDTO createStudent(Student student) {

        student.setId(null); // Ensure new entity
        student.setPassword(encoder.encode(student.getPassword()));
        if (student.getDepartment() != null && student.getDepartment().getId() != null) {
            Department department = departmentRepository.findById(student.getDepartment().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid department ID"));
            student.setDepartment(department);
        } else {
            student.setDepartment(null); // Allow null department
        }
        Student dbStudent = studentRepository.save(student);
        return new StudentDTO(dbStudent);
    }

    @Override
    public StudentDTO updateStudent(Student student) {

        Long studentId = student.getId();
        student.setPassword(encoder.encode(student.getPassword()));
        if(!studentRepository.existsById(studentId)){
            throw new ResourceNotFoundException("Student not found with ID: "+studentId);
        }

        Student s = studentRepository.saveAndFlush(student);
        return new StudentDTO(s);
    }

    @Override
    public void deleteStudent(Long studentId) {
        if(!studentRepository.existsById(studentId)){
            throw new ResourceNotFoundException("Student not found with ID: "+studentId);
        }
        studentRepository.deleteById(studentId);
    }

    public StudentDTO verify(StudentLoginDTO studentLoginDTO) {
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(studentLoginDTO.getEmail(), studentLoginDTO.getPassword())
        );

        if (!authentication.isAuthenticated()) {
            throw new BadCredentialsException("Invalid Email or password.");
        }

        Student dbStudent = studentRepository.findByEmail(studentLoginDTO.getEmail());
        if (dbStudent == null) {
            throw new UsernameNotFoundException("Student not found with email: " + studentLoginDTO.getEmail());
        }
        String jwtToken = jwtService.generateToken(studentLoginDTO.getEmail());
        return new StudentDTO(dbStudent,jwtToken);
    }

}
