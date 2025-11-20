package com.movieticketing.backend.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateMovieRequest {

    @NotBlank
    private String name;

    private String description;

    private Integer publicationYear;

    private String director;

    private String actor;

    private String genre;

    private String coverPhoto;

    @NotNull
    private Long typeId;

    private Long accountId;

    @DecimalMin("0.0")
    private BigDecimal ticketPrice;
}