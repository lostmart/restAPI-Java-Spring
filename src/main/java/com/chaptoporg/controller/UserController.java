package com.chaptoporg.controller;

import com.chaptoporg.model.User;
import com.chaptoporg.service.UserService;
import com.chaptoporg.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        User user = userService.findByEmail(loginRequest.getEmail());

        if (user == null || !user.getPassword().equals(loginRequest.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        String token = jwtService.createToken(user.getEmail());
        return ResponseEntity.ok(token);
    }

    @GetMapping("/me/{id}")
    public User getUserById(@PathVariable String id) {
        return userService.getUserById(Long.parseLong(id));
    }

    public static class LoginRequest {
        private String email;
        private String password;

        // Getters and setters

        public String getEmail() {
            return email;
        }

        public String getPassword() {
            return password;
        }

        // Constructor

        public LoginRequest(String email, String password) {
            this.email = email;
            this.password = password;
        }

        public static class LoginResponse {
            private String token;

            public LoginResponse(String token) {
                this.token = token;
            }

            public String getToken() {
                return token;
            }
        }

    }

}
