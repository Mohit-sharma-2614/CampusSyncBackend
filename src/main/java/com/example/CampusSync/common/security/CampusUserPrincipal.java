package com.example.CampusSync.common.security;

import com.example.CampusSync.student.entity.Student;
import com.example.CampusSync.teacher.model.Teacher;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;


public class CampusUserPrincipal implements UserDetails {

    private final Student student;
    private final Teacher teacher;

    // Constructor for Teacher
    public CampusUserPrincipal(Teacher teacher) {
        this.teacher = teacher;
        this.student = null; // Ensure student is null
    }

    // Constructor for Student
    public CampusUserPrincipal(Student student) {
        this.student = student;
        this.teacher = null; // Ensure teacher is null
    }

    // --- UserDetails Methods ---

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // For simplicity, assuming all authenticated users have a "USER" role.
        return Collections.singleton(new SimpleGrantedAuthority("USER"));
        // Best practice to prefix roles with "ROLE_" for Spring Security's hasRole() method
    }

    @Override
    public String getPassword() {
        if (student != null) {
            return student.getPassword();
        } else if (teacher != null) {
            return teacher.getPassword();
        }
        return null; // Or throw an exception if neither is set (shouldn't happen with proper construction)
    }

    @Override
    public String getUsername() {
        if (student != null) {
            return student.getEmail();
        } else if (teacher != null) {
            return teacher.getEmail();
        }
        return null; // Or throw an exception
    }
}
