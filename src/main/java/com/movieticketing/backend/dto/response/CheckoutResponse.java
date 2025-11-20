package com.movieticketing.backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutResponse {
    private BigDecimal totalAmount;
    private int processedItems;
    private List<TransactionResponse> transactions;
}