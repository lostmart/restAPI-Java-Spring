package com.chaptoporg.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO used to receive rental listing data from the client.
 * Includes validation rules and Swagger metadata for documentation.
 */
@Data
@Schema(description = "Request object for creating or updating a rental listing")
public class RentalRequest {

    @NotBlank(message = "Name is required")
    @Schema(description = "Title or name of the rental listing", example = "Seaside Apartment")
    private String name;

    @Positive(message = "Surface must be positive")
    @Schema(description = "Surface area in square meters", example = "45")
    private Integer surface;

    @Positive(message = "Price must be positive")
    @Schema(description = "Monthly rental price in euros", example = "850")
    private Integer price;

    @NotBlank(message = "Description is required")
    @Schema(description = "Detailed description of the property", example = "Bright studio with balcony and ocean view")
    private String description;

    @NotNull(message = "Owner ID is required")
    @Schema(description = "ID of the property owner", example = "3")
    private Long ownerId;

    @NotNull(message = "Picture is required")
    @Schema(description = "Image file representing the rental", type = "string", format = "binary")
    private MultipartFile picture;
}
