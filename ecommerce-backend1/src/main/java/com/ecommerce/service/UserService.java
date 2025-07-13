package com.ecommerce.service;

import com.ecommerce.dto.UserRequestDTO;
import com.ecommerce.dto.UserResponseDTO;

import java.util.List;

public interface UserService {
    UserResponseDTO registerUser(UserRequestDTO request);
    
    UserResponseDTO getUserById(Long userId);
    
    List<UserResponseDTO> getAllUsers();
    
    List<UserResponseDTO> getUsersByRole(String role);
    
    UserResponseDTO updateUser(Long userId, UserRequestDTO userDTO);
    
    void deleteUser(Long userId);
}

