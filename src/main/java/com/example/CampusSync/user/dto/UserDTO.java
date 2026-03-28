package com.example.CampusSync.user.dto;

import com.example.CampusSync.user.model.Role;
import com.example.CampusSync.user.model.Status;
import com.example.CampusSync.user.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private Role role;
    private Status status;
    private Timestamp createdAt;

    public UserDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.role = user.getRole();
        this.status = user.getStatus();
        this.createdAt = user.getCreatedAt();
    }
}
