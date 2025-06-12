package com.chaptoporg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


import com.chaptoporg.model.User;
import com.chaptoporg.service.UserService;





@RestController
@RequestMapping("/api/auth/")

public class UserController {

    @Autowired
    private UserService userService;

    // @GetMapping
    // public List<User> getAllUsers() {
    //     return userService.getAllUsers();
    // }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @GetMapping
    public String getMethodName(){
        return "Hey yo !";
    }
    

    
    @GetMapping("/me/{id}")
    public User getUserById(@PathVariable String id) {
        return userService.getUserById(Long.parseLong(id));
    }

}

