package com.movieticketing.backend.service;

import com.movieticketing.backend.dto.request.RatingRequest;
import com.movieticketing.backend.dto.response.RatingResponse;
import com.movieticketing.backend.entity.Account;
import com.movieticketing.backend.entity.Movie;
import com.movieticketing.backend.entity.Rating;
import com.movieticketing.backend.exception.ResourceNotFoundException;
import com.movieticketing.backend.repository.MovieRepository;
import com.movieticketing.backend.repository.RatingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RatingService {

    private final RatingRepository ratingRepository;
    private final MovieRepository movieRepository;
    private final AccountService accountService;

    public RatingResponse add(Long movieId, RatingRequest request) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new ResourceNotFoundException("Movie", movieId));
        Account account = accountService.findEntityById(request.getAccountId());

        Rating rating = Rating.builder()
                .movie(movie)
                .account(account)
                .rating(request.getRating())
                .comment(request.getComment())
                .build();
        return toResponse(ratingRepository.save(rating));
    }

    public List<RatingResponse> listByMovie(Long movieId) {
        if (!movieRepository.existsById(movieId)) {
            throw new ResourceNotFoundException("Movie", movieId);
        }
        return ratingRepository.findByMovieIdOrderByRatingDateDesc(movieId).stream()
                .map(this::toResponse)
                .toList();
    }

    private RatingResponse toResponse(Rating rating) {
        return RatingResponse.builder()
                .id(rating.getId())
                .rating(rating.getRating())
                .comment(rating.getComment())
                .ratingDate(rating.getRatingDate())
                .movieId(rating.getMovie().getId())
                .accountId(rating.getAccount().getId())
                .build();
    }
}