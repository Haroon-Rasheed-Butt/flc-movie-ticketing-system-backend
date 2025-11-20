package com.movieticketing.backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItemResponse {
    private Long id;
    private Integer quantity;
    private Boolean purchased;
    private LocalDateTime purchaseDate;
    private Long movieId;
    private String movieName;
    private BigDecimal ticketPrice;
}