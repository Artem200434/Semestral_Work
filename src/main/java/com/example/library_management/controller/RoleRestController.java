package com.example.library_management.controller;

import com.example.library_management.dto.RoleDTO;
import com.example.library_management.model.Role;
import com.example.library_management.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/roles")
public class RoleRestController {

    @Autowired
    private RoleService roleService;

    @GetMapping
    public List<RoleDTO> getAllRoles() {
        return roleService.getAllRoles().stream()
                .map(role -> new RoleDTO(
                        role.getId(),
                        role.getRoleName()
                ))
                .collect(Collectors.toList());
    }
}
