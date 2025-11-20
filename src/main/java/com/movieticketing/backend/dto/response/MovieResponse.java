package com.movieticketing.backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieResponse {
    private Long id;
    private String name;
    private String description;
    private Integer publicationYear;
    private String director;
    private String actor;
    private String genre;
    private String coverPhoto;
    private BigDecimal ticketPrice;
    private Long typeId;
    private String typeName;
    private Long ownerId;
    private String ownerUsername;
}