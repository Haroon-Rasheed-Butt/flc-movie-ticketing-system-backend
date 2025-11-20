package com.movieticketing.backend.controller;

import com.movieticketing.backend.dto.request.CartItemRequest;
import com.movieticketing.backend.dto.response.ApiResponse;
import com.movieticketing.backend.dto.response.CartItemResponse;
import com.movieticketing.backend.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/cart")
    public ResponseEntity<ApiResponse<CartItemResponse>> add(@Valid @RequestBody CartItemRequest request) {
        CartItemResponse response = cartService.add(request);
        return ResponseEntity.ok(ApiResponse.success("Cart item added", response));
    }

    @GetMapping("/accounts/{id}/cart")
    public ResponseEntity<ApiResponse<List<CartItemResponse>>> findByAccount(@PathVariable Long id) {
        List<CartItemResponse> responses = cartService.findByAccount(id);
        return ResponseEntity.ok(ApiResponse.success(responses));
    }
}