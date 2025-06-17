package com.chaptoporg.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRegistrationRequest {
    @NotBlank @Email
    private String email;
    
    @NotBlank @Size(min = 5, max = 20)
    private String password;
    
    @NotBlank
    private String name;
    // Add other registration fields as needed
}
