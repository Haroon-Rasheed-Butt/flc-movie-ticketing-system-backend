package com.flc.movietickets.backend.service;

import com.flc.movietickets.backend.entity.Theater;
import com.flc.movietickets.backend.repository.TheaterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TheaterService {
    private final TheaterRepository theaterRepository;

    public List<Theater> getAllActiveTheaters() {
        return theaterRepository.findByIsActiveTrue();
    }

    public Optional<Theater> getTheaterById(Long id) {
        return theaterRepository.findById(id);
    }

    public List<Theater> getTheatersByCity(String city) {
        return theaterRepository.findByCityAndIsActiveTrue(city);
    }

    public List<Theater> searchTheaters(String name) {
        return theaterRepository.findByNameContainingIgnoreCaseAndIsActiveTrue(name);
    }

    public Theater createTheater(Theater theater) {
        return theaterRepository.save(theater);
    }

    public Theater updateTheater(Long id, Theater theaterDetails) {
        Theater theater = theaterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Theater not found with id: " + id));
        
        theater.setName(theaterDetails.getName());
        theater.setLocation(theaterDetails.getLocation());
        theater.setCity(theaterDetails.getCity());
        theater.setAddress(theaterDetails.getAddress());
        theater.setTotalScreens(theaterDetails.getTotalScreens());
        theater.setIsActive(theaterDetails.getIsActive());
        
        return theaterRepository.save(theater);
    }

    public void deleteTheater(Long id) {
        Theater theater = theaterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Theater not found with id: " + id));
        theater.setIsActive(false);
        theaterRepository.save(theater);
    }
}
