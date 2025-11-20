package com.movieticketing.backend.repository;

import com.movieticketing.backend.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    List<Rating> findByMovieIdOrderByRatingDateDesc(Long movieId);
}