package com.chaptoporg.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO representing the response returned after a successful login.
 * Contains the JWT token used for authenticated requests.
 */
@Schema(description = "Response object containing the JWT token after a successful login")
public class LoginResponse {

    /**
     * JWT token to be used in Authorization headers for authenticated requests.
     */
    @Schema(description = "JWT token for authentication", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String token;

    /**
     * Constructs a LoginResponse with the provided JWT token.
     *
     * @param token the JWT token string
     */
    public LoginResponse(String token) {
        this.token = token;
    }

    /**
     * Returns the JWT token.
     *
     * @return the JWT token
     */
    public String getToken() {
        return token;
    }
}
