package com.example.CampusSync.user.service;

import com.example.CampusSync.user.dto.UserDTO;
import com.example.CampusSync.user.dto.UserInputDTO;
import com.example.CampusSync.user.model.Status;

import java.util.List;

public interface UserService {
    List<UserDTO> getAllUsers();
    UserDTO getUserById(Long id);
    UserDTO createUser(UserInputDTO userInputDTO);
    UserDTO updateUser(UserInputDTO userInputDTO);
    UserDTO updateUserStatus(Long id, Status status);
    void deleteUser(Long id);
}
