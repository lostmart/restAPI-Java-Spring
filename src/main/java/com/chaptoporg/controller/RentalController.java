package com.chaptoporg.controller;

import com.chaptoporg.dto.RentalRequest;
import com.chaptoporg.dto.RentalResponse;
import com.chaptoporg.dto.RentalUpdateRequest;
import com.chaptoporg.model.Rental;
import com.chaptoporg.service.RentalService;
import org.springframework.util.StringUtils;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

/**
 * Controller that handles CRUD operations for Rental entities.
 */
@RestController
@RequestMapping("/api/rentals")
@Tag(name = "Rentals", description = "Endpoints for managing rentals")
public class RentalController {

    @Value("${app.base-url:http://localhost:3000}") // Make configurable
    private String baseUrl;

    @Autowired
    private RentalService rentalService;

    /**
     * Returns a list of all rentals.
     * 
     * @return all rental records
     */
    @Operation(summary = "Get all rentals")
    @ApiResponse(responseCode = "200", description = "List of rentals")
    @GetMapping
    public ResponseEntity<List<RentalResponse>> getAllRentals() {
        List<RentalResponse> responseList = StreamSupport
                .stream(rentalService.getAllRentals().spliterator(), false)
                .map(rental -> new RentalResponse(rental, baseUrl))
                .collect(Collectors.toList());

        return ResponseEntity.ok(responseList);
    }

    /**
     * Returns a specific rental by its ID.
     * 
     * @param id ID of the rental
     * @return rental if found
     */
    @Operation(summary = "Get rental by ID")
    @ApiResponse(responseCode = "200", description = "Rental found")
    @ApiResponse(responseCode = "404", description = "Rental not found")
    @GetMapping("/{id}")
    public Optional<Rental> getRentalById(@PathVariable Long id) {
        return rentalService.getRentalById(id);
    }

    /**
     * Creates a new rental entry with an image upload.
     * 
     * @param rentalRequest rental data
     * @param bindingResult validation results
     * @return created rental or error details
     */
    @Operation(summary = "Create a new rental with image upload")
    @ApiResponse(responseCode = "200", description = "Rental created")
    @ApiResponse(responseCode = "400", description = "Validation error")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createRental(
            @Valid @ModelAttribute RentalRequest rentalRequest,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        MultipartFile picture = rentalRequest.getPicture();
        if (picture == null || picture.isEmpty()) {
            return ResponseEntity.badRequest().body("Picture file is required");
        }

        String contentType = picture.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            return ResponseEntity.badRequest().body(
                    Map.of(
                            "error", "Invalid file type",
                            "message", "Only image files are allowed",
                            "receivedType", contentType != null ? contentType : "null"));
        }

        try {
            String rawFilename = picture.getOriginalFilename();
            if (rawFilename == null || rawFilename.isBlank()) {
                return ResponseEntity.badRequest().body("Invalid or missing file name.");
            }
            String originalFilename = StringUtils.cleanPath(rawFilename);
            String fileName = UUID.randomUUID() + "_" + originalFilename;

            Path uploadPath = Paths.get("uploads");
            Files.createDirectories(uploadPath);

            Path targetLocation = uploadPath.resolve(fileName);
            Files.copy(picture.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            Rental rental = convertToEntity(rentalRequest, fileName);
            Rental savedRental = rentalService.saveRental(rental);

            return ResponseEntity.ok(savedRental);

        } catch (IOException ex) {
            return ResponseEntity.internalServerError().body("File upload failed: " + ex.getMessage());
        }
    }

    /**
     * Converts a RentalRequest DTO to a Rental entity.
     * 
     * @param rentalRequest the request DTO
     * @param fileName      uploaded image filename
     * @return Rental entity
     */
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

    /**
     * Deletes a rental by its ID.
     * 
     * @param id ID of the rental
     */
    @Operation(summary = "Delete rental by ID")
    @ApiResponse(responseCode = "204", description = "Rental deleted")
    @DeleteMapping("/{id}")
    public void deleteRental(@PathVariable Long id) {
        rentalService.deleteRental(id);
    }

    /**
     * Updates an existing rental.
     * 
     * @param id     ID of the rental
     * @param rental rental data to update
     * @return updated rental or newly created rental
     */
    @Operation(summary = "Update an existing rental")
    @ApiResponse(responseCode = "200", description = "Rental updated or created")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateRental(
            @PathVariable Long id,
            @Valid @RequestBody RentalUpdateRequest updateRequest,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        return rentalService.getRentalById(id)
                .map(existing -> {
                    existing.setName(updateRequest.getName());
                    existing.setSurface(updateRequest.getSurface());
                    existing.setPrice(updateRequest.getPrice());
                    existing.setDescription(updateRequest.getDescription());
                    // existing.setOwnerId(updateRequest.getOwnerId());
                    return ResponseEntity.ok(rentalService.saveRental(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }
}