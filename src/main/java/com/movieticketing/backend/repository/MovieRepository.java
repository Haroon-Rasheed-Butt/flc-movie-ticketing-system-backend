package com.movieticketing.backend.repository;

import com.movieticketing.backend.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}