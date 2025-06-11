package com.chaptoporg.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chaptoporg.model.User;
import com.chaptoporg.repo.UserRepo;

@Service
public class UserService {
    
    @Autowired
    private UserRepo userRepo;
    
    public List<User> getAllUsers() {
        return userRepo.findAll(); // This uses the built-in findAll() method
    }
}
