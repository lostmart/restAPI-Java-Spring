package com.chaptoporg.dto;

public class LoginResponse {
    private String token;

    // Constructor
    public LoginResponse(String token, Long userId, String email, String name) {
        this.token = token;
    }

    // Getters (required for JSON serialization)
    public String getToken() {
        return token;
    }
}
