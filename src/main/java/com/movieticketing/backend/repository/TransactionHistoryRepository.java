package com.movieticketing.backend.repository;

import com.movieticketing.backend.entity.TransactionHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory, Long> {
    List<TransactionHistory> findByAccountIdOrderByTransactionDateDesc(Long accountId);
}