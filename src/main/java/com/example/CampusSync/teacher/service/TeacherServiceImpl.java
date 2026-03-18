package com.example.CampusSync.teacher.service;

import java.util.List;

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
import com.example.CampusSync.teacher.dto.TeacherResponseDTO;
import com.example.CampusSync.teacher.dto.TeacherLoginDTO;
import com.example.CampusSync.teacher.dto.TeacherRequestDTO;
import com.example.CampusSync.teacher.model.Teacher;
import com.example.CampusSync.teacher.repository.TeacherRepository;
import com.example.CampusSync.refreshtoken.service.RefreshTokenService;
import com.example.CampusSync.refreshtoken.model.RefreshTokens;

import jakarta.transaction.Transactional;

@Service
@ComponentScan
public class TeacherServiceImpl implements TeacherService{
    @Autowired
    private TeacherRepository teacherRepository;

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
    public List<TeacherResponseDTO> getAllTeacher() {
        List<Teacher> teacher = teacherRepository.findAll();
        return teacher.stream()
                .map(TeacherResponseDTO::new)
                .toList();
    }

    @Override
    public TeacherResponseDTO getTeacher(Long teacherId) {
        Teacher t = teacherRepository.findById(teacherId)
                .orElseThrow(()-> new ResourceNotFoundException("Teacher not found with ID: "+teacherId));
        return new TeacherResponseDTO(t);
    }

    @Override
    @Transactional
    public TeacherResponseDTO updateTeacher(Long teacherId, TeacherRequestDTO request) {

        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found with ID: " + teacherId));
                
        // Update User info if provided
        User user = teacher.getUser();
        if (request.getName() != null) user.setName(request.getName());
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            user.setPasswordHash(encoder.encode(request.getPassword()));
        }

        // Update Teacher info
        if (request.getEmployeeId() != null) teacher.setEmployeeId(request.getEmployeeId());
        if (request.getDepartmentId() != null) teacher.setDepartmentId(request.getDepartmentId());
        if (request.getDesignation() != null) teacher.setDesignation(request.getDesignation());

        Teacher t = teacherRepository.save(teacher);
        return new TeacherResponseDTO(t);
    }

    @Override
    @Transactional
    public TeacherResponseDTO createTeacher(TeacherRequestDTO request) {
        // Check if user already exists
        if (userRepository.findByEmail(request.getEmail()) != null) {
            throw new IllegalArgumentException("Email already in use");
        }

        // Create User entity
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPasswordHash(encoder.encode(request.getPassword()));
        user.setRole(Role.TEACHER);
        user.setStatus(Status.ACTIVE);
        
        // Create Teacher entity
        Teacher teacher = new Teacher();
        teacher.setUser(user);
        teacher.setEmployeeId(request.getEmployeeId());
        teacher.setDepartmentId(request.getDepartmentId());
        teacher.setDesignation(request.getDesignation());

        // Save teacher (which will save user due to CascadeType.ALL)
        Teacher dbTeacher = teacherRepository.save(teacher);
        return new TeacherResponseDTO(dbTeacher);
    }

    @Override
    public void deleteTeacher(Long teacherId) {

        if(!teacherRepository.existsById(teacherId)){
            throw new ResourceNotFoundException("Teacher not found with ID: "+teacherId);
        }

        teacherRepository.deleteById(teacherId);
    }

    @Override
    public TeacherResponseDTO verify(TeacherLoginDTO teacherLogin) {
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(teacherLogin.getEmail(), teacherLogin.getPassword())
        );

        if (!authentication.isAuthenticated()) {
            throw new BadCredentialsException("Invalid Email or password.");
        }

        Teacher dbTeacher = teacherRepository.findByEmail(teacherLogin.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Teacher not found with email: " + teacherLogin.getEmail()));
                
        String jwtToken = jwtService.generateToken(teacherLogin.getEmail(), dbTeacher.getUser().getRole().name());
        RefreshTokens refreshToken = refreshTokenService.createRefreshToken(teacherLogin.getEmail(), "Android App");
        
        return new TeacherResponseDTO(dbTeacher, jwtToken, refreshToken.getToken());
    }
}
