package com.flc.movietickets.backend.service;

import com.flc.movietickets.backend.entity.Movie;
import com.flc.movietickets.backend.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;

    public List<Movie> getAllActiveMovies() {
        return movieRepository.findByIsActiveTrue();
    }

    public Optional<Movie> getMovieById(Long id) {
        return movieRepository.findById(id);
    }

    public List<Movie> getMoviesByGenre(String genre) {
        return movieRepository.findByGenreAndIsActiveTrue(genre);
    }

    public List<Movie> getMoviesByLanguage(String language) {
        return movieRepository.findByLanguageAndIsActiveTrue(language);
    }

    public List<Movie> searchMovies(String title) {
        return movieRepository.findByTitleContainingIgnoreCaseAndIsActiveTrue(title);
    }

    public Movie createMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    public Movie updateMovie(Long id, Movie movieDetails) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found with id: " + id));
        
        movie.setTitle(movieDetails.getTitle());
        movie.setDescription(movieDetails.getDescription());
        movie.setDuration(movieDetails.getDuration());
        movie.setGenre(movieDetails.getGenre());
        movie.setLanguage(movieDetails.getLanguage());
        movie.setReleaseDate(movieDetails.getReleaseDate());
        movie.setDirector(movieDetails.getDirector());
        movie.setCast(movieDetails.getCast());
        movie.setPosterUrl(movieDetails.getPosterUrl());
        movie.setTrailerUrl(movieDetails.getTrailerUrl());
        movie.setRating(movieDetails.getRating());
        movie.setIsActive(movieDetails.getIsActive());
        
        return movieRepository.save(movie);
    }

    public void deleteMovie(Long id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found with id: " + id));
        movie.setIsActive(false);
        movieRepository.save(movie);
    }
}
