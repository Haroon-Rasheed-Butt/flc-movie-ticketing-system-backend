package com.movieticketing.backend.repository;

import com.movieticketing.backend.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByAccountIdAndPurchasedFalse(Long accountId);
    List<CartItem> findByAccountId(Long accountId);
    List<CartItem> findByIdInAndAccountId(List<Long> ids, Long accountId);
}