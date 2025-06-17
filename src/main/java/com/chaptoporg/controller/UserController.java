package com.chaptoporg.controller;

import com.chaptoporg.dto.ErrorResponse;
import com.chaptoporg.dto.LoginRequest;
import com.chaptoporg.dto.LoginResponse;
import com.chaptoporg.exception.EmailAlreadyExistsException;
import com.chaptoporg.model.User;
import com.chaptoporg.service.UserService;
import com.chaptoporg.service.JwtService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * Controller that handles authentication and user account operations.
 */
@RestController
@RequestMapping("/api/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    /**
     * Registers a new user.
     *
     * @param user the user to register
     * @return a JWT token if registration is successful, or error message if email already exists
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            // Attempt to create the user
            User createdUser = userService.createUser(user);

            // Generate a JWT token for the new user
            String token = jwtService.generateToken(createdUser);

            // Return the token in a login-like response format
            LoginResponse response = new LoginResponse(token);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (EmailAlreadyExistsException e) {
            // Handle duplicate email case
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("Email already exists"));
        }
    }

    /**
     * Logs a user in by validating credentials and issuing a JWT.
     *
     * @param loginRequest contains the user's email and password
     * @return JWT token if authentication is successful, or an error message if not
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        // Find the user by email
        User user = userService.findByEmail(loginRequest.getEmail());

        // Authenticate the user
        if (user == null || !userService.authenticate(loginRequest.getEmail(), loginRequest.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse("Invalid email or password"));
        }

        // Generate JWT token
        String token = jwtService.generateToken(user);

        // Return token to client
        LoginResponse response = new LoginResponse(token);
        return ResponseEntity.ok(response);
    }

    /**
     * Returns the authenticated user's information.
     *
     * @param authentication contains the current user's authentication context
     * @return the authenticated user's data
     */
    @GetMapping("/me")
    public ResponseEntity<?> getMe(Authentication authentication) {
        // Get user email from the authentication context
        String email = authentication.getName();

        // Look up the user by email
        User user = userService.findByEmail(email);

        // Return user info
        return ResponseEntity.ok(user);
    }
}
