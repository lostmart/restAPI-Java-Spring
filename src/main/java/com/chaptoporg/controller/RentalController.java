package com.chaptoporg.controller;

import com.chaptoporg.dto.LoginRequest;
import com.chaptoporg.dto.RentalRequest;
import com.chaptoporg.model.Rental;
import com.chaptoporg.service.RentalService;
import org.springframework.util.StringUtils;




import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;


import org.springframework.security.core.Authentication;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.Optional;


import java.nio.file.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {


    @Autowired
    private RentalService rentalService;

    @GetMapping
    public Iterable<Rental> getAllRentals() {
        return rentalService.getAllRentals();
    }

    @GetMapping("/{id}")
    public Optional<Rental> getRentalById(@PathVariable Long id) {
        return rentalService.getRentalById(id);
    }

    @GetMapping("/debug")
    public ResponseEntity<String> debug(Authentication authentication) {
        String username = authentication.getName(); // principal, typically the email
        String roles = authentication.getAuthorities().toString();

        System.out.println("JwtAuthFilter ran for: " + username);

        return ResponseEntity.ok("Authenticated as: " + username + ", roles: " + roles);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createRental(
            @Valid @ModelAttribute RentalRequest rentalRequest,
            BindingResult bindingResult) {
        
        // Validate input
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        MultipartFile picture = rentalRequest.getPicture();

        // Validate file exists and has content type
        if (picture == null || picture.isEmpty()) {
            return ResponseEntity.badRequest().body("Picture file is required");
        }

        String contentType = picture.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            return ResponseEntity.badRequest().body(
                Map.of(
                    "error", "Invalid file type",
                    "message", "Only image files are allowed",
                    "receivedType", contentType != null ? contentType : "null"
                )
            );
        }

        try {
            // Generate unique filename
            String originalFilename = StringUtils.cleanPath(
                rentalRequest.getPicture().getOriginalFilename()
            );
            String fileName = UUID.randomUUID() + "_" + originalFilename;
            
            // Create upload directory if needed
            Path uploadPath = Paths.get("uploads");
            Files.createDirectories(uploadPath);
            
            // Save file
            Path targetLocation = uploadPath.resolve(fileName);
            Files.copy(
                rentalRequest.getPicture().getInputStream(),
                targetLocation,
                StandardCopyOption.REPLACE_EXISTING
            );
            
            // Convert and save rental
            Rental rental = convertToEntity(rentalRequest, fileName);
            Rental savedRental = rentalService.saveRental(rental);
            
            return ResponseEntity.ok(savedRental);
            
        } catch (IOException ex) {
            return ResponseEntity.internalServerError()
                .body("File upload failed: " + ex.getMessage());
        }
    }

    private Rental convertToEntity(RentalRequest rentalRequest, String fileName) {
        Rental rental = new Rental();
        rental.setName(rentalRequest.getName());
        rental.setSurface(rentalRequest.getSurface());
        rental.setPrice(rentalRequest.getPrice());
        rental.setDescription(rentalRequest.getDescription());
        rental.setOwnerId(rentalRequest.getOwnerId().intValue());
        rental.setPicture(fileName);
        return rental;
    }

    @DeleteMapping("/{id}")
    public void deleteRental(@PathVariable Long id) {
        rentalService.deleteRental(id);
    }

    @PutMapping("/{id}")
    public Rental updateRental(@PathVariable Long id, @RequestBody Rental rental) {
        return rentalService.getRentalById(id)
                .map(existing -> {
                    existing.setName(rental.getName());
                    existing.setSurface(rental.getSurface());
                    existing.setPrice(rental.getPrice());
                    existing.setPicture(rental.getPicture());
                    existing.setDescription(rental.getDescription());
                    existing.setOwnerId(rental.getOwnerId());
                    return rentalService.saveRental(existing);
                })
                .orElseGet(() -> {
                    rental.setId(id.intValue());
                    return rentalService.saveRental(rental);
                });
    }

}
