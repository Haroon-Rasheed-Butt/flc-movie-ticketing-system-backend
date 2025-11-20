package com.movieticketing.backend.controller;

import com.movieticketing.backend.dto.request.CheckoutRequest;
import com.movieticketing.backend.dto.response.ApiResponse;
import com.movieticketing.backend.dto.response.CheckoutResponse;
import com.movieticketing.backend.service.CheckoutService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/checkout")
@RequiredArgsConstructor
public class CheckoutController {

    private final CheckoutService checkoutService;

    @PostMapping
    public ResponseEntity<ApiResponse<CheckoutResponse>> checkout(@Valid @RequestBody CheckoutRequest request) {
        CheckoutResponse response = checkoutService.checkout(request);
        return ResponseEntity.ok(ApiResponse.success("Checkout completed", response));
    }
}