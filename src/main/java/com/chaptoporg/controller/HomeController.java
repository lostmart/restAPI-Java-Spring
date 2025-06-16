package com.chaptoporg.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Home Controller - Provides basic API entry point and health check
 * 
 * <p>
 * This controller handles the root endpoint and serves as a welcome point
 * for the ChâTop REST API. It's primarily used to verify API availability.
 * </p>
 * 
 * @author Martin P
 * @version 1.0
 * @since 2023-06-15
 */
@RestController
public class HomeController {

    /**
     * Returns a welcome message for the API root endpoint     * 
     * 
     * @return String - Welcome message with API identification
     * @apiNote This is a public endpoint that doesn't require authentication
     * @exampleResponse Example response: "🍀 Welcome to the ChâTop REST API!"
     */

    @GetMapping("/")
    public String welcome() {
        return "🍀 Welcome to the Welcome to the ChâTop REST API!";
    }
}
