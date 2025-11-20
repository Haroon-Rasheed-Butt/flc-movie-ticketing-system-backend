package com.movieticketing.backend.service;

import com.movieticketing.backend.dto.response.TransactionResponse;
import com.movieticketing.backend.entity.TransactionHistory;
import com.movieticketing.backend.repository.TransactionHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionHistoryRepository repository;
    private final AccountService accountService;

    public List<TransactionResponse> findByAccount(Long accountId) {
        accountService.findEntityById(accountId);
        return repository.findByAccountIdOrderByTransactionDateDesc(accountId).stream()
                .map(this::toResponse)
                .toList();
    }

    private TransactionResponse toResponse(TransactionHistory history) {
        return TransactionResponse.builder()
                .id(history.getId())
                .totalAmount(history.getTotalAmount())
                .transactionDate(history.getTransactionDate())
                .status(history.getStatus())
                .movieId(history.getMovie().getId())
                .accountId(history.getAccount().getId())
                .build();
    }
}