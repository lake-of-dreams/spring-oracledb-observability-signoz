package com.example.ratings.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.common.model.ProductRating;
import com.example.ratings.model.*;
import com.example.ratings.repository.RatingsRepository;
import com.example.ratings.service.StoreManagerService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/ratings")
@Slf4j
public class RatingsController {

    final RatingsRepository ratingsRepository;
    final StoreManagerService storeManagerService;

    public RatingsController(RatingsRepository ratingRepository, StoreManagerService storeManagerService) {
        this.ratingsRepository = ratingRepository;
        this.storeManagerService = storeManagerService;
    }

    /**
     * Get all Ratings.
     *
     * @return List all ratings
     */
    @GetMapping("")
    public List<Rating> getAllRatings() {
        return ratingsRepository.findAll();
    }

    /**
     * Create a rating.
     *
     * @param rating Rating object.
     * @return Returns HTTP Status code or the URI of the created object.
     */
    @PostMapping("")
    public ResponseEntity<Rating> createRating(@RequestBody Rating rating) {
        log.info("Rating is " + rating);
        if ((rating.getId() == null) || (rating.getId().getProductId() <= 0)
                || (rating.getId().getUserId() <= 0) || (rating.getRating() == null)
                || (rating.getRating().isNaN())) {
            return new ResponseEntity<>(rating, HttpStatus.BAD_REQUEST);
        }
        boolean exists = ratingsRepository.existsById(rating.getId());
        if (!exists) {
            try {
                if (!storeManagerService.rateProduct(new ProductRating().productId(rating.getId().getProductId())
                        .rating(rating.getRating()))) {
                    return new ResponseEntity<>(rating, HttpStatus.INTERNAL_SERVER_ERROR);
                }
                Rating newRating = ratingsRepository.saveAndFlush(rating);
                URI location = ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{productId}/{userId}")
                        .buildAndExpand(newRating.getId().getProductId(), newRating
                                .getId().getUserId())
                        .toUri();
                return ResponseEntity.created(location).build();
            } catch (Exception e) {
                return new ResponseEntity<>(rating, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>(rating, HttpStatus.CONFLICT);
        }
    }

    /**
     * Find an rating by Product Id and User Id.
     *
     * @param productId Product Id
     * @param userId    User Id
     * @return An rating
     */
    @GetMapping("/{productId}/{userId}")
    public ResponseEntity<Rating> getRatingById(@PathVariable("productId") long productId,
            @PathVariable("userId") long userId) {
        Optional<Rating> ratingData = ratingsRepository.findById(new RatingId().productId(productId).userId(userId));
        try {
            return ratingData.map(rating -> new ResponseEntity<>(rating, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Delete an Rating with specific Product Id and User Id.
     *
     * @param productId Product Id
     * @param userId    User Id
     * @return HTTP Status Code
     */
    @DeleteMapping("/{productId}/{userId}")
    public ResponseEntity<HttpStatus> deleteRating(@PathVariable("productId") long productId,
            @PathVariable("userId") long userId) {
        try {
            ratingsRepository.deleteById(new RatingId().productId(productId).userId(userId));
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}