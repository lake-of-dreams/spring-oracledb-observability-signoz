package com.example.ratings.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ratings.model.Rating;
import com.example.ratings.model.RatingId;

public interface RatingsRepository extends JpaRepository<Rating, RatingId> {
    List<Rating> findByIdUserId(long userId);

    List<Rating> findByIdProductId(long productId);

}