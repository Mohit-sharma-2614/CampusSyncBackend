package com.example.CampusSync.common.security;

import com.example.CampusSync.student.entity.Student;
import com.example.CampusSync.student.repository.StudentRepository;
import com.example.CampusSync.teacher.model.Teacher;
import com.example.CampusSync.teacher.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // First, try to find a student with the given email
        Student student = studentRepository.findByEmail(email);

        if (student != null) {
            // If a student is found, return a CampusUserPrincipal for the student
            return new CampusUserPrincipal(student);
        }

        // If no student is found, try to find a teacher with the given email
        Teacher teacher = teacherRepository.findByEmail(email);

        if (teacher != null) {
            // If a teacher is found, return a CampusUserPrincipal for the teacher
            return new CampusUserPrincipal(teacher);
        }

        // If neither a student nor a teacher is found, throw an exception
        System.out.println("User not found with email: " + email);
        throw new UsernameNotFoundException("User not found with email: " + email);
    }
}
