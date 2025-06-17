package com.chaptoporg.dto;

public class LoginResponse {
    private String token;
    private Long userId;
    private String email;
    private String name;

    // Full constructor
    public LoginResponse(String token, Long userId, String email, String name) {
        this.token = token;
        this.userId = userId;
        this.email = email;
        this.name = name;
    }

    // Getters (required for JSON serialization)
    public String getToken() {
        return token;
    }

    public Long getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }
}
