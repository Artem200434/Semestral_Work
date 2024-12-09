package com.example.library_management.service;

import com.example.library_management.dto.UserDTO;
import com.example.library_management.model.Role;
import com.example.library_management.model.User;
import com.example.library_management.repository.RoleRepository;
import com.example.library_management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    // Retrieve all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Retrieve user by ID
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    // Save or update user
    public User saveUser(User user) {
        if (user.getId() != null && userRepository.existsById(user.getId())) {
            Optional<User> existingUser = userRepository.findById(user.getId());
            if (existingUser.isPresent()) {
                User updatedUser = existingUser.get();
                updatedUser.setUsername(user.getUsername());
                updatedUser.setEmail(user.getEmail());
                updatedUser.setRole(user.getRole());

                if (user.getPassword() == null || user.getPassword().isEmpty()) {
                    updatedUser.setPassword(existingUser.get().getPassword());
                } else {
                    updatedUser.setPassword(user.getPassword());
                }

                return userRepository.save(updatedUser);
            }
        }

        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null for new users");
        }
        return userRepository.save(user);
    }

    // Delete user by ID
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    // Save a new user
    public User saveNewUser(User user) {
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty for new users");
        }

        if (user.getId() != null && userRepository.existsById(user.getId())) {
            throw new IllegalArgumentException("User with this ID already exists. Use the update functionality instead.");
        }

        return userRepository.save(user);
    }

    // Find user by username
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // Register a new user with a default role
    public User registerUser(User user) {
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty for new users");
        }

        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new IllegalArgumentException("User with this username already exists.");
        }

        if (user.getRole() == null) {
            Role defaultRole = roleRepository.findByRoleName("USER");
            if (defaultRole == null) {
                throw new IllegalStateException("Default role 'USER' not found in the database");
            }
            user.setRole(defaultRole);
        }

        return userRepository.save(user);
    }

    // Add a new user
    public void addUser(UserDTO userDTO) {
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new IllegalArgumentException("Email already exists!");
        }
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword()); // Consider hashing the password
        user.setRole(determineRoleFromName(userDTO.getRoleName())); // Determine role based on input
        userRepository.save(user);
    }

    private Role determineRoleFromName(String roleName) {
        if (roleName == null || roleName.isEmpty()) {
            throw new IllegalArgumentException("Role name cannot be null or empty");
        }

        switch (roleName.toUpperCase()) {
            case "ADMIN":
                return roleRepository.findById(1L).orElseThrow(() -> new IllegalArgumentException("Role ID 1 not found"));
            case "USER":
                return roleRepository.findById(2L).orElseThrow(() -> new IllegalArgumentException("Role ID 2 not found"));
            default:
                throw new IllegalArgumentException("Invalid role name: " + roleName);
        }
    }
}
