package com.flc.movietickets.backend.service;

import com.flc.movietickets.backend.entity.Movie;
import com.flc.movietickets.backend.entity.Show;
import com.flc.movietickets.backend.entity.Theater;
import com.flc.movietickets.backend.repository.MovieRepository;
import com.flc.movietickets.backend.repository.ShowRepository;
import com.flc.movietickets.backend.repository.TheaterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShowService {
    private final ShowRepository showRepository;
    private final MovieRepository movieRepository;
    private final TheaterRepository theaterRepository;

    public List<Show> getAllActiveShows() {
        return showRepository.findByIsActiveTrue();
    }

    public Optional<Show> getShowById(Long id) {
        return showRepository.findById(id);
    }

    public List<Show> getShowsByMovie(Long movieId) {
        return showRepository.findByMovieIdAndIsActiveTrue(movieId);
    }

    public List<Show> getShowsByTheater(Long theaterId) {
        return showRepository.findByTheaterIdAndIsActiveTrue(theaterId);
    }

    public List<Show> getShowsByMovieAndTheater(Long movieId, Long theaterId) {
        return showRepository.findByMovieIdAndTheaterIdAndIsActiveTrue(movieId, theaterId);
    }

    public List<Show> getShowsByDateRange(LocalDateTime start, LocalDateTime end) {
        return showRepository.findByShowTimeBetweenAndIsActiveTrue(start, end);
    }

    public Show createShow(Show show) {
        // Validate movie exists
        Movie movie = movieRepository.findById(show.getMovie().getId())
                .orElseThrow(() -> new RuntimeException("Movie not found with id: " + show.getMovie().getId()));
        
        // Validate theater exists
        Theater theater = theaterRepository.findById(show.getTheater().getId())
                .orElseThrow(() -> new RuntimeException("Theater not found with id: " + show.getTheater().getId()));
        
        show.setMovie(movie);
        show.setTheater(theater);
        show.setAvailableSeats(show.getTotalSeats());
        
        return showRepository.save(show);
    }

    public Show updateShow(Long id, Show showDetails) {
        Show show = showRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Show not found with id: " + id));
        
        show.setShowTime(showDetails.getShowTime());
        show.setScreenNumber(showDetails.getScreenNumber());
        show.setTotalSeats(showDetails.getTotalSeats());
        show.setTicketPrice(showDetails.getTicketPrice());
        show.setIsActive(showDetails.getIsActive());
        
        return showRepository.save(show);
    }

    public void deleteShow(Long id) {
        Show show = showRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Show not found with id: " + id));
        show.setIsActive(false);
        showRepository.save(show);
    }

    public boolean updateAvailableSeats(Long showId, int seatsToBook) {
        Show show = showRepository.findById(showId)
                .orElseThrow(() -> new RuntimeException("Show not found with id: " + showId));
        
        if (show.getAvailableSeats() >= seatsToBook) {
            show.setAvailableSeats(show.getAvailableSeats() - seatsToBook);
            showRepository.save(show);
            return true;
        }
        return false;
    }
}
