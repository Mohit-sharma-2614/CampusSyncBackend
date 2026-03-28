package com.example.CampusSync.user.service;

import com.example.CampusSync.common.exceptions.ResourceNotFoundException;
import com.example.CampusSync.user.dto.UserDTO;
import com.example.CampusSync.user.dto.UserInputDTO;
import com.example.CampusSync.user.model.Status;
import com.example.CampusSync.user.model.User;
import com.example.CampusSync.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return new UserDTO(user);
    }

    @Override
    public UserDTO createUser(UserInputDTO userInputDTO) {
        if (userInputDTO.getEmail() == null || userInputDTO.getPassword() == null) {
            throw new IllegalArgumentException("Email and password are required");
        }
        
        // Ensure email isn't already taken
        if (userRepository.findByEmail(userInputDTO.getEmail()) != null) {
            throw new IllegalArgumentException("Email is already registered");
        }

        User user = new User();
        user.setName(userInputDTO.getName());
        user.setEmail(userInputDTO.getEmail());
        user.setRole(userInputDTO.getRole());
        user.setStatus(userInputDTO.getStatus() != null ? userInputDTO.getStatus() : Status.ACTIVE);
        
        // Hash the raw password before saving
        user.setPasswordHash(passwordEncoder.encode(userInputDTO.getPassword()));

        User savedUser = userRepository.save(user);
        return new UserDTO(savedUser);
    }

    @Override
    public UserDTO updateUser(UserInputDTO userInputDTO) {
        if (userInputDTO.getId() == null) {
            throw new IllegalArgumentException("User ID must be provided for update");
        }

        User existingUser = userRepository.findById(userInputDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userInputDTO.getId()));

        if (userInputDTO.getName() != null) {
            existingUser.setName(userInputDTO.getName());
        }
        if (userInputDTO.getEmail() != null) {
            // Check if email collides with someone else
            User duplicate = userRepository.findByEmail(userInputDTO.getEmail());
            if (duplicate != null && !duplicate.getId().equals(existingUser.getId())) {
                throw new IllegalArgumentException("Email is already taken by another user");
            }
            existingUser.setEmail(userInputDTO.getEmail());
        }
        if (userInputDTO.getRole() != null) {
            existingUser.setRole(userInputDTO.getRole());
        }
        if (userInputDTO.getStatus() != null) {
            existingUser.setStatus(userInputDTO.getStatus());
        }
        if (userInputDTO.getPassword() != null && !userInputDTO.getPassword().isEmpty()) {
            existingUser.setPasswordHash(passwordEncoder.encode(userInputDTO.getPassword()));
        }

        User updatedUser = userRepository.save(existingUser);
        return new UserDTO(updatedUser);
    }

    @Override
    public UserDTO updateUserStatus(Long id, Status status) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        
        existingUser.setStatus(status);
        User updatedUser = userRepository.save(existingUser);
        return new UserDTO(updatedUser);
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }
}
