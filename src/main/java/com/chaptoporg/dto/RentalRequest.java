package com.chaptoporg.dto;  // Make sure this matches your package structure

import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class RentalRequest {
    @NotBlank(message = "Name is required")
    private String name;
    
    @Positive(message = "Surface must be positive")
    private Integer surface;
    
    @Positive(message = "Price must be positive")
    private Integer price;
    
    @NotBlank(message = "Description is required")
    private String description;
    
    @NotNull(message = "Owner ID is required")
    private Long ownerId;
    
    @NotNull(message = "Picture is required")
    private MultipartFile picture;
}