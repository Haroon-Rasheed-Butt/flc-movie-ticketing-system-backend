package com.flc.movietickets.backend.repository;

import com.flc.movietickets.backend.entity.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ShowRepository extends JpaRepository<Show, Long> {
    List<Show> findByIsActiveTrue();
    List<Show> findByMovieIdAndIsActiveTrue(Long movieId);
    List<Show> findByTheaterIdAndIsActiveTrue(Long theaterId);
    List<Show> findByShowTimeBetweenAndIsActiveTrue(LocalDateTime start, LocalDateTime end);
    List<Show> findByMovieIdAndTheaterIdAndIsActiveTrue(Long movieId, Long theaterId);
}
