package com.chaptoporg.dto;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import lombok.Data;

@Data
public class RentalResponse {
    private Integer id;
    private String name;
    private Integer surface;
    private Integer price;
    private String picture; // This will now store the full URL
    private String description;
    private Integer ownerId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public RentalResponse(com.chaptoporg.model.Rental rental, String baseUrl) {
        this.id = rental.getId();
        this.name = rental.getName();
        this.surface = rental.getSurface();
        this.price = rental.getPrice();
        this.description = rental.getDescription();
        this.ownerId = rental.getOwnerId();

        // Convert dates
        Date created = rental.getCreatedAt();
        Date updated = rental.getUpdatedAt();
        this.createdAt = (created != null) ? created.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
                : null;
        this.updatedAt = (updated != null) ? updated.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
                : null;

        // Construct full picture URL
        this.picture = baseUrl + "/uploads/" + rental.getPicture();
    }

    // Getters (and setters if needed)
    public String getPicture() {
        return picture;
    }
}