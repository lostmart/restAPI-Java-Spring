package com.chaptoporg.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
    @Email(message = "Invalid email format")
    @NotBlank(message = "Email cannot be empty")
    private String email;

    @NotBlank(message = "Password cannot be empty")
    private String password;

    public LoginRequest() {
        // Default constructor
    }

    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}