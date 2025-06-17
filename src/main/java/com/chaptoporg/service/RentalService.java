package com.chaptoporg.service;

import com.chaptoporg.model.Rental;
import com.chaptoporg.repo.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service class for handling business logic related to Rental entities.
 * Provides CRUD operations by interacting with the RentalRepository.
 */
@Service
public class RentalService {

    @Autowired
    private RentalRepository rentalRepository;

    /**
     * Retrieves all rentals from the database.
     *
     * @return an iterable list of all Rental entities
     */
    public Iterable<Rental> getAllRentals() {
        return rentalRepository.findAll();
    }

    /**
     * Retrieves a specific rental by its ID.
     *
     * @param id the ID of the rental
     * @return an Optional containing the rental if found, or empty if not found
     */
    public Optional<Rental> getRentalById(Long id) {
        return rentalRepository.findById(id);
    }

    /**
     * Saves a new or existing rental to the database.
     *
     * @param rental the rental entity to save
     * @return the saved rental entity
     */
    public Rental saveRental(Rental rental) {
        return rentalRepository.save(rental);
    }

    /**
     * Deletes a rental from the database by its ID.
     *
     * @param id the ID of the rental to delete
     */
    public void deleteRental(Long id) {
        rentalRepository.deleteById(id);
    }
}