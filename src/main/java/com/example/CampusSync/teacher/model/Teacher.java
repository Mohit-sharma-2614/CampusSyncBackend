package com.example.CampusSync.teacher.model;


import java.time.LocalDateTime;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.example.CampusSync.user.model.User;

@Entity
@Table(name = "teachers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Teacher {
    @Id
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false, name = "employee_id")
    private String employeeId;

    @Column(nullable = false, name = "department_id")
    private String departmentId;

    private String designation;
}
