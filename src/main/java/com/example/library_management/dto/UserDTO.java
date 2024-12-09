package com.example.library_management.dto;

public class UserDTO {

    private Long id;
    private String username;
    private String email;
    private String roleName;
    private String password;

    // Default constructor
    public UserDTO() {
    }

    // Constructor for all fields
    public UserDTO(Long id, String username, String email, String roleName) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.roleName = roleName;
    }

    // Constructor without ID (useful for new users)
    public UserDTO(String username, String email, String roleName, String password) {
        this.username = username;
        this.email = email;
        this.roleName = roleName;
        this.password = password;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
