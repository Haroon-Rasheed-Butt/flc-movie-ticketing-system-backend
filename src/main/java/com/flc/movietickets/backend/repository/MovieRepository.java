package com.flc.movietickets.backend.repository;

import com.flc.movietickets.backend.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findByIsActiveTrue();
    List<Movie> findByGenreAndIsActiveTrue(String genre);
    List<Movie> findByLanguageAndIsActiveTrue(String language);
    List<Movie> findByTitleContainingIgnoreCaseAndIsActiveTrue(String title);
}
