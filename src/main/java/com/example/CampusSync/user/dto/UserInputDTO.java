package com.example.CampusSync.user.dto;

import com.example.CampusSync.user.model.Role;
import com.example.CampusSync.user.model.Status;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserInputDTO {
    private Long id; // Optional, used for updates
    private String name;
    private String email;
    private String password; // Will be hashed via BCrypt in service layer
    private Role role;
    private Status status;
}
