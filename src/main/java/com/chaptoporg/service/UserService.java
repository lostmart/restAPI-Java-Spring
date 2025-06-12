package com.chaptoporg.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chaptoporg.exception.ResourceNotFoundException;
import com.chaptoporg.model.User;
import com.chaptoporg.repo.UserRepo;

@Service
public class UserService {
    
    @Autowired
    private UserRepo userRepo;

    public User createUser(User user) {
        return userRepo.save(user); // This uses the built-in save() method
    }
    
    public List<User> getAllUsers() {
        return userRepo.findAll(); // This uses the built-in findAll() method
    }

    public User getUserById(long id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }
    
    
}
