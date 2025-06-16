package com.chaptoporg.controller;

import com.chaptoporg.dto.ErrorResponse;
import com.chaptoporg.dto.LoginRequest;
import com.chaptoporg.dto.LoginResponse;
import com.chaptoporg.model.User;
import com.chaptoporg.service.UserService;

import jakarta.validation.Valid;

import com.chaptoporg.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/api/auth")

public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        // Important: no passwords !! in the response
        createdUser.setPassword(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        // Find user by email
        User user = userService.findByEmail(loginRequest.getEmail());

        // Verify user exists and password matches
        if (user == null || !userService.authenticate(loginRequest.getEmail(), loginRequest.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse("Invalid email or password"));
        }

        // Generate JWT token
        String token = jwtService.generateToken(user);

        // Create response DTO
        LoginResponse response = new LoginResponse(
                token,
                user.getId(),
                user.getEmail(),
                user.getName());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<?> getMe(Authentication authentication) {
        String email = authentication.getName();
        User user = userService.findByEmail(email);
        return ResponseEntity.ok(user);
    }

}
