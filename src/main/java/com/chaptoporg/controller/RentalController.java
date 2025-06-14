package com.chaptoporg.controller;

import com.chaptoporg.model.Rental;
import com.chaptoporg.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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

    @PostMapping
    public Rental createRental(@RequestBody Rental rental) {
        return rentalService.saveRental(rental);
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
