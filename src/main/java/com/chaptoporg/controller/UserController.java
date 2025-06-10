package com.chaptoporg.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;




@RestController

public class UserController {
    @GetMapping("/users")
    public String welcome() {
        return "🍀 Welcome to users endpoint !!";
    }

    @GetMapping("/users/{id}")
    public String welcomeById(@PathVariable String id) {
        return "🍀 Welcome to users endpoint !! " + id;
    }
    
    @PostMapping("/users")
    public String postMethodName(@RequestBody String entity) {
        //TODO: process POST request
        
        return entity;
    }
    
}

