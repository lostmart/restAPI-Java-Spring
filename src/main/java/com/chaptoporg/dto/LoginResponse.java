package com.chaptoporg.dto;

public class LoginResponse {
    private String token;

    // Full constructor
    public LoginResponse(String token) {
        this.token = token;
    }

    // Getters (required for JSON serialization)
    public String getToken() {
        return token;
    }
}
