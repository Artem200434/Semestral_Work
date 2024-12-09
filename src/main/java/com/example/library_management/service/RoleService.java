package com.example.library_management.service;

import com.example.library_management.model.Role;
import com.example.library_management.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    // Get all roles
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    // Get role by ID
    public Role getRoleById(Long id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Role not found with ID: " + id));
    }

    // Save or update a role
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    // Delete role by ID
    public void deleteRole(Long id) {
        if (!roleRepository.existsById(id)) {
            throw new IllegalArgumentException("Role not found with ID: " + id);
        }
        roleRepository.deleteById(id);
    }
}
