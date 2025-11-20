package com.flc.movietickets.backend.repository;

import com.flc.movietickets.backend.entity.Theater;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TheaterRepository extends JpaRepository<Theater, Long> {
    List<Theater> findByIsActiveTrue();
    List<Theater> findByCityAndIsActiveTrue(String city);
    List<Theater> findByNameContainingIgnoreCaseAndIsActiveTrue(String name);
}
