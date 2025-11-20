package com.movieticketing.backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RatingResponse {
    private Long id;
    private Integer rating;
    private String comment;
    private LocalDateTime ratingDate;
    private Long movieId;
    private Long accountId;
}