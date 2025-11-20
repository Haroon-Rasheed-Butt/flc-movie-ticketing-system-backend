package com.movieticketing.backend.controller;

import com.movieticketing.backend.dto.request.CreateMovieRequest;
import com.movieticketing.backend.dto.request.RatingRequest;
import com.movieticketing.backend.dto.request.UpdateMovieRequest;
import com.movieticketing.backend.dto.response.ApiResponse;
import com.movieticketing.backend.dto.response.MovieResponse;
import com.movieticketing.backend.dto.response.PageResponse;
import com.movieticketing.backend.dto.response.RatingResponse;
import com.movieticketing.backend.service.MovieService;
import com.movieticketing.backend.service.RatingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;
    private final RatingService ratingService;

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<MovieResponse>>> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        PageResponse<MovieResponse> payload = movieService.list(page, size);
        return ResponseEntity.ok(ApiResponse.success(payload));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<MovieResponse>> create(@Valid @RequestBody CreateMovieRequest request) {
        MovieResponse response = movieService.create(request);
        return ResponseEntity.ok(ApiResponse.success("Movie saved", response));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<MovieResponse>> update(@PathVariable Long id,
                                                           @Valid @RequestBody UpdateMovieRequest request) {
        MovieResponse response = movieService.update(id, request);
        return ResponseEntity.ok(ApiResponse.success("Movie updated", response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        movieService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Movie deleted", null));
    }

    @PostMapping("/{id}/ratings")
    public ResponseEntity<ApiResponse<RatingResponse>> addRating(@PathVariable Long id,
                                                                  @Valid @RequestBody RatingRequest request) {
        RatingResponse response = ratingService.add(id, request);
        return ResponseEntity.ok(ApiResponse.success("Rating added", response));
    }

    @GetMapping("/{id}/ratings")
    public ResponseEntity<ApiResponse<List<RatingResponse>>> listRatings(@PathVariable Long id) {
        List<RatingResponse> responses = ratingService.listByMovie(id);
        return ResponseEntity.ok(ApiResponse.success(responses));
    }
}