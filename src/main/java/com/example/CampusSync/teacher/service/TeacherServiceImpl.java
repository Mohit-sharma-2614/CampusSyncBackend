package com.example.CampusSync.teacher.service;

import com.example.CampusSync.common.exceptions.BadCredentialsException;
import com.example.CampusSync.common.exceptions.ResourceAlreadyExistException;
import com.example.CampusSync.common.exceptions.ResourceNotFoundException;
import com.example.CampusSync.common.security.JWTService;
import com.example.CampusSync.department.model.Department;
import com.example.CampusSync.department.repository.DepartmentRepository;
import com.example.CampusSync.student.dto.StudentDTO;
import com.example.CampusSync.student.entity.Student;
import com.example.CampusSync.teacher.dto.TeacherDTO;
import com.example.CampusSync.teacher.dto.TeacherRegistrationRequest;
import com.example.CampusSync.teacher.model.Teacher;
import com.example.CampusSync.teacher.repository.TeacherRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@ComponentScan
public class TeacherServiceImpl implements TeacherService{
    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    AuthenticationManager authManager;

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JWTService jwtService;

    @Override
    public List<TeacherDTO> getAllTeacher() {
        List<Teacher> teacher = teacherRepository.findAll();
        return teacher.stream()
                .map(TeacherDTO::new)
                .toList();
    }

    @Override
    public TeacherDTO getTeacher(Long teacherId) {
        Teacher t = teacherRepository.findById(teacherId)
                .orElseThrow(()-> new ResourceNotFoundException("Teacher not found with ID: "+teacherId));
        return new TeacherDTO(t);
    }

    @Override
    public TeacherDTO updateTeacher(Teacher teacher) {

        Long teacherID = teacher.getId();

        if(!teacherRepository.existsById(teacherID)){
            throw new ResourceNotFoundException("Teacher not found with ID: "+teacherID);
        }
        teacher.setPassword(encoder.encode(teacher.getPassword()));

        Teacher t = teacherRepository.saveAndFlush(teacher);
        return new TeacherDTO(t);
    }

    @Transactional // Ensure this method is transactional for fetching and saving
    public TeacherDTO createTeacher(TeacherRegistrationRequest request) {
        // 1. Fetch the Department entity using the provided departmentId
        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new IllegalArgumentException("Department not found with ID: " + request.getDepartmentId()));

        // 2. Create the Teacher entity
        Teacher teacher = new Teacher();
        teacher.setName(request.getName());
        teacher.setEmail(request.getEmail());
        teacher.setPassword(encoder.encode(request.getPassword())); // Hash the password!
        teacher.setDepartment(department); // <-- Set the fetched Department object

        // If you're not using @CreationTimestamp, set it here:
        teacher.setCreatedAt(LocalDateTime.now());

        // 3. Save the Teacher entity
        Teacher t = teacherRepository.save(teacher);
        return new TeacherDTO(t);
    }

    @Override
    public void deleteTeacher(Long teacherId) {

        if(!teacherRepository.existsById(teacherId)){
            throw new ResourceNotFoundException("Teacher not found with ID: "+teacherId);
        }

        teacherRepository.deleteById(teacherId);
    }

    public TeacherDTO verify(Teacher teacher) {
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(teacher.getEmail(), teacher.getPassword())
        );

        if (!authentication.isAuthenticated()) {
            throw new BadCredentialsException("Invalid Email or password.");
        }

        Teacher dbStudent = teacherRepository.findByEmail(teacher.getEmail());
        if (dbStudent == null) {
            throw new UsernameNotFoundException("Student not found with email: " + teacher.getEmail());
        }
        String jwtToken = jwtService.generateToken(teacher.getEmail());
        return new TeacherDTO(dbStudent,jwtToken);
    }
}
