package com.chaptoporg.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.chaptoporg.exception.EmailAlreadyExistsException;
import com.chaptoporg.exception.ResourceNotFoundException;
import com.chaptoporg.model.User;
import com.chaptoporg.repo.UserRepo;

@Service
public class UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    // Constructor injection
    public UserService(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(User user) {
    if (userRepo.existsByEmail(user.getEmail())) {
        throw new EmailAlreadyExistsException("This email is already taken");
    }

    String hashedPassword = passwordEncoder.encode(user.getPassword());
    user.setPassword(hashedPassword);
    return userRepo.save(user);
}

    public List<User> getAllUsers() {
        return userRepo.findAll(); // This uses the built-in findAll() method
    }

    public User getUserById(long id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    // authenticate
    public boolean authenticate(String email, String rawPassword) {
        User user = userRepo.findByEmail(email);
        return user != null && passwordEncoder.matches(rawPassword, user.getPassword());
    }

    public User findByEmail(String email) {
        return userRepo.findByEmail(email);
    }

}
