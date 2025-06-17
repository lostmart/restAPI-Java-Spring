package com.chaptoporg.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO used for user registration requests.
 * Carries the required user information submitted during the sign-up process.
 */
@Data
@Schema(description = "Payload for registering a new user")
public class UserRegistrationRequest {

    /**
     * User's email address.
     * Must not be blank and must follow a valid email format.
     */
    @NotBlank
    @Email
    @Schema(description = "User's email address", example = "user@example.com")
    private String email;

    /**
     * User's password.
     * Must not be blank. Length must be between 5 and 20 characters.
     */
    @NotBlank
    @Size(min = 5, max = 20)
    @Schema(description = "User's password", example = "securePass123")
    private String password;

    /**
     * User's full name.
     * Must not be blank.
     */
    @NotBlank
    @Schema(description = "User's full name", example = "Jane Doe")
    private String name;
}
