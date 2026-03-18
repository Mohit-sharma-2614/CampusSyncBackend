package com.example.CampusSync.student.service;

import java.util.List;

import com.example.CampusSync.student.dto.StudentRequestDTO;
import com.example.CampusSync.user.model.Role;
import com.example.CampusSync.user.model.Status;
import com.example.CampusSync.user.model.User;
import com.example.CampusSync.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.CampusSync.common.exceptions.BadCredentialsException;
import com.example.CampusSync.common.exceptions.ResourceNotFoundException;
import com.example.CampusSync.common.security.JWTService;
import com.example.CampusSync.student.dto.StudentResponseDTO;
import com.example.CampusSync.student.dto.StudentLoginDTO;
import com.example.CampusSync.student.entity.Student;
import com.example.CampusSync.student.repository.StudentRepository;
import com.example.CampusSync.refreshtoken.service.RefreshTokenService;
import com.example.CampusSync.refreshtoken.model.RefreshTokens;
import jakarta.transaction.Transactional;

@ComponentScan
@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JWTService jwtService;
    
    @Autowired
    private RefreshTokenService refreshTokenService;

    @Override
    public List<StudentResponseDTO> getAllStudents() {
        List<Student> s = studentRepository.findAll();
        return s.stream()
                .map(StudentResponseDTO::new)
                .toList();
    }

    @Override
    public StudentResponseDTO getStudent(Long studentId) {
        Student s = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: "+studentId));
        return new StudentResponseDTO(s);
    }

    @Override
    @Transactional
    public StudentResponseDTO createStudent(StudentRequestDTO studentDto) {
        // Check if user already exists
        if (userRepository.findByEmail(studentDto.getEmail()) != null) {
            throw new IllegalArgumentException("Email already in use");
        }

        // Create User entity
        User user = new User();
        user.setName(studentDto.getName());
        user.setEmail(studentDto.getEmail());
        user.setPasswordHash(encoder.encode(studentDto.getPassword()));
        user.setRole(Role.STUDENT);
        user.setStatus(Status.ACTIVE);
        
        // Create Student entity
        Student student = new Student();
        student.setUser(user);
        student.setRollNumber(studentDto.getRollNumber());
        student.setDepartmentId(studentDto.getDepartmentId());
        student.setYear(studentDto.getYear());
        student.setSemester(studentDto.getSemester());
        student.setSection(studentDto.getSection());

        // Save student (which will save user due to CascadeType.ALL)
        Student dbStudent = studentRepository.save(student);
        return new StudentResponseDTO(dbStudent);
    }

    @Override
    @Transactional
    public StudentResponseDTO updateStudent(Long studentId, StudentRequestDTO studentDto) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: "+studentId));

        // Update User info if provided
        User user = student.getUser();
        if (studentDto.getName() != null) user.setName(studentDto.getName());
        if (studentDto.getPassword() != null && !studentDto.getPassword().isEmpty()) {
            user.setPasswordHash(encoder.encode(studentDto.getPassword()));
        }

        // Update Student info
        if (studentDto.getRollNumber() != null) student.setRollNumber(studentDto.getRollNumber());
        if (studentDto.getDepartmentId() != null) student.setDepartmentId(studentDto.getDepartmentId());
        if (studentDto.getYear() != null) student.setYear(studentDto.getYear());
        if (studentDto.getSemester() != null) student.setSemester(studentDto.getSemester());
        if (studentDto.getSection() != null) student.setSection(studentDto.getSection());

        Student s = studentRepository.save(student);
        return new StudentResponseDTO(s);
    }

    @Override
    public void deleteStudent(Long studentId) {
        if(!studentRepository.existsById(studentId)){
            throw new ResourceNotFoundException("Student not found with ID: "+studentId);
        }
        studentRepository.deleteById(studentId);
    }

    @Override
    public StudentResponseDTO verify(StudentLoginDTO studentLoginDTO) {
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(studentLoginDTO.getEmail(), studentLoginDTO.getPassword())
        );

        if (!authentication.isAuthenticated()) {
            throw new BadCredentialsException("Invalid Email or password.");
        }

        Student dbStudent = studentRepository.findByEmail(studentLoginDTO.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Student not found with email: " + studentLoginDTO.getEmail()));
                
        String jwtToken = jwtService.generateToken(studentLoginDTO.getEmail(), dbStudent.getUser().getRole().name());
        RefreshTokens refreshToken = refreshTokenService.createRefreshToken(studentLoginDTO.getEmail(), "Android App");
        
        return new StudentResponseDTO(dbStudent, jwtToken, refreshToken.getToken());
    }

}
