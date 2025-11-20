package com.movieticketing.backend.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItemRequest {

    @NotNull
    private Long accountId;

    @NotNull
    private Long movieId;

    @NotNull
    @Min(1)
    private Integer quantity;
}