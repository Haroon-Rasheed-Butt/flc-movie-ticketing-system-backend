package com.movieticketing.backend.service;

import com.movieticketing.backend.dto.request.CartItemRequest;
import com.movieticketing.backend.dto.response.CartItemResponse;
import com.movieticketing.backend.entity.Account;
import com.movieticketing.backend.entity.CartItem;
import com.movieticketing.backend.entity.Movie;
import com.movieticketing.backend.exception.ResourceNotFoundException;
import com.movieticketing.backend.repository.CartItemRepository;
import com.movieticketing.backend.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartItemRepository cartItemRepository;
    private final MovieRepository movieRepository;
    private final AccountService accountService;

    public CartItemResponse add(CartItemRequest request) {
        Account account = accountService.findEntityById(request.getAccountId());
        Movie movie = movieRepository.findById(request.getMovieId())
                .orElseThrow(() -> new ResourceNotFoundException("Movie", request.getMovieId()));

        CartItem cartItem = CartItem.builder()
                .account(account)
                .movie(movie)
                .quantity(request.getQuantity())
                .purchased(false)
                .build();

        return toResponse(cartItemRepository.save(cartItem));
    }

    public List<CartItemResponse> findByAccount(Long accountId) {
        return cartItemRepository.findByAccountId(accountId).stream()
                .map(this::toResponse)
                .toList();
    }

    public List<CartItem> findPendingForAccount(Long accountId) {
        return cartItemRepository.findByAccountIdAndPurchasedFalse(accountId);
    }

    public List<CartItem> findByIdsAndAccount(List<Long> ids, Long accountId) {
        return cartItemRepository.findByIdInAndAccountId(ids, accountId);
    }

    public void saveAll(List<CartItem> items) {
        cartItemRepository.saveAll(items);
    }

    private CartItemResponse toResponse(CartItem cartItem) {
        return CartItemResponse.builder()
                .id(cartItem.getId())
                .quantity(cartItem.getQuantity())
                .purchased(cartItem.getPurchased())
                .purchaseDate(cartItem.getPurchaseDate())
                .movieId(cartItem.getMovie().getId())
                .movieName(cartItem.getMovie().getName())
                .ticketPrice(cartItem.getMovie().getTicketPrice())
                .build();
    }
}