package com.movieticketing.backend.service;

import com.movieticketing.backend.dto.request.CheckoutRequest;
import com.movieticketing.backend.dto.response.CheckoutResponse;
import com.movieticketing.backend.dto.response.TransactionResponse;
import com.movieticketing.backend.entity.Account;
import com.movieticketing.backend.entity.CartItem;
import com.movieticketing.backend.entity.TransactionHistory;
import com.movieticketing.backend.exception.ResourceNotFoundException;
import com.movieticketing.backend.repository.TransactionHistoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CheckoutService {

    private final CartService cartService;
    private final TransactionHistoryRepository transactionHistoryRepository;
    private final AccountService accountService;

    @Transactional
    public CheckoutResponse checkout(CheckoutRequest request) {
        Account account = accountService.findEntityById(request.getAccountId());
        List<CartItem> items = determineCartItems(request, request.getCartItemIds());

        if (items.isEmpty()) {
            throw new ResourceNotFoundException("CartItem", request.getAccountId());
        }

        BigDecimal totalAmount = BigDecimal.ZERO;
        List<TransactionResponse> transactions = new ArrayList<>();

        for (CartItem cartItem : items) {
            BigDecimal pricePerItem = cartItem.getMovie().getTicketPrice() == null
                    ? BigDecimal.ZERO
                    : cartItem.getMovie().getTicketPrice();
            BigDecimal subtotal = pricePerItem.multiply(BigDecimal.valueOf(cartItem.getQuantity()));
            totalAmount = totalAmount.add(subtotal);

            cartItem.setPurchased(true);
            cartItem.setPurchaseDate(LocalDateTime.now());

            TransactionHistory transaction = TransactionHistory.builder()
                    .account(account)
                    .movie(cartItem.getMovie())
                    .totalAmount(subtotal)
                    .status("COMPLETED")
                    .build();

            TransactionHistory saved = transactionHistoryRepository.save(transaction);
            transactions.add(toResponse(saved));
        }

        cartService.saveAll(items);

        return CheckoutResponse.builder()
                .totalAmount(totalAmount)
                .processedItems(items.size())
                .transactions(transactions)
                .build();
    }

    private List<CartItem> determineCartItems(CheckoutRequest request, List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return cartService.findPendingForAccount(request.getAccountId());
        }
        List<CartItem> items = cartService.findByIdsAndAccount(ids, request.getAccountId());
        if (items.size() != ids.size()) {
            throw new ResourceNotFoundException("CartItem", request.getAccountId());
        }
        return items;
    }

    private TransactionResponse toResponse(TransactionHistory history) {
        return TransactionResponse.builder()
                .id(history.getId())
                .transactionDate(history.getTransactionDate())
                .totalAmount(history.getTotalAmount())
                .status(history.getStatus())
                .movieId(history.getMovie().getId())
                .accountId(history.getAccount().getId())
                .build();
    }
}