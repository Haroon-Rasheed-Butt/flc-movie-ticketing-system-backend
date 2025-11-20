package com.movieticketing.backend.repository;

import com.movieticketing.backend.entity.MovieType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieTypeRepository extends JpaRepository<MovieType, Long> {
}