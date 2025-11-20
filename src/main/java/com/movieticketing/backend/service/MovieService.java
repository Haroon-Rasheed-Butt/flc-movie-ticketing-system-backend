package com.movieticketing.backend.service;

import com.movieticketing.backend.dto.request.CreateMovieRequest;
import com.movieticketing.backend.dto.request.UpdateMovieRequest;
import com.movieticketing.backend.dto.response.MovieResponse;
import com.movieticketing.backend.dto.response.PageResponse;
import com.movieticketing.backend.entity.Account;
import com.movieticketing.backend.entity.Movie;
import com.movieticketing.backend.entity.MovieType;
import com.movieticketing.backend.exception.ResourceNotFoundException;
import com.movieticketing.backend.repository.MovieRepository;
import com.movieticketing.backend.repository.MovieTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;
    private final MovieTypeRepository typeRepository;
    private final AccountService accountService;

    public PageResponse<MovieResponse> list(int page, int size) {
        Page<Movie> moviePage = movieRepository.findAll(PageRequest.of(page, size));
        List<MovieResponse> content = moviePage.stream().map(this::toResponse).toList();

        return PageResponse.<MovieResponse>builder()
                .content(content)
                .page(moviePage.getNumber())
                .size(moviePage.getSize())
                .totalElements(moviePage.getTotalElements())
                .totalPages(moviePage.getTotalPages())
                .build();
    }

    public MovieResponse create(CreateMovieRequest request) {
        MovieType type = typeRepository.findById(request.getTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("MovieType", request.getTypeId()));

        Account owner = null;
        if (request.getAccountId() != null) {
            owner = accountService.findEntityById(request.getAccountId());
        }

        Movie movie = Movie.builder()
                .name(request.getName())
                .description(request.getDescription())
                .publicationYear(request.getPublicationYear())
                .director(request.getDirector())
                .actor(request.getActor())
                .genre(request.getGenre())
                .coverPhoto(request.getCoverPhoto())
                .ticketPrice(request.getTicketPrice() == null ? BigDecimal.ZERO : request.getTicketPrice())
                .type(type)
                .owner(owner)
                .build();

        return toResponse(movieRepository.save(movie));
    }

    public MovieResponse update(Long id, UpdateMovieRequest request) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movie", id));

        MovieType type = typeRepository.findById(request.getTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("MovieType", request.getTypeId()));

        Account owner = null;
        if (request.getAccountId() != null) {
            owner = accountService.findEntityById(request.getAccountId());
        }

        movie.setName(request.getName());
        movie.setDescription(request.getDescription());
        movie.setPublicationYear(request.getPublicationYear());
        movie.setDirector(request.getDirector());
        movie.setActor(request.getActor());
        movie.setGenre(request.getGenre());
        movie.setCoverPhoto(request.getCoverPhoto());
        if (request.getTicketPrice() != null) {
            movie.setTicketPrice(request.getTicketPrice());
        }
        movie.setType(type);
        movie.setOwner(owner);

        return toResponse(movieRepository.save(movie));
    }

    public void delete(Long id) {
        if (!movieRepository.existsById(id)) {
            throw new ResourceNotFoundException("Movie", id);
        }
        movieRepository.deleteById(id);
    }

    private MovieResponse toResponse(Movie movie) {
        Account owner = movie.getOwner();
        return MovieResponse.builder()
                .id(movie.getId())
                .name(movie.getName())
                .description(movie.getDescription())
                .publicationYear(movie.getPublicationYear())
                .director(movie.getDirector())
                .actor(movie.getActor())
                .genre(movie.getGenre())
                .coverPhoto(movie.getCoverPhoto())
                .ticketPrice(movie.getTicketPrice())
                .typeId(movie.getType().getId())
                .typeName(movie.getType().getName())
                .ownerId(owner == null ? null : owner.getId())
                .ownerUsername(owner == null ? null : owner.getUsername())
                .build();
    }
}