package com.movieticketing.backend.controller;

import com.movieticketing.backend.dto.request.CreateAccountRequest;
import com.movieticketing.backend.dto.response.AccountResponse;
import com.movieticketing.backend.dto.response.ApiResponse;
import com.movieticketing.backend.dto.response.TransactionResponse;
import com.movieticketing.backend.service.AccountService;
import com.movieticketing.backend.service.TransactionService;
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
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<ApiResponse<AccountResponse>> create(@Valid @RequestBody CreateAccountRequest request) {
        AccountResponse response = accountService.create(request);
        return ResponseEntity.ok(ApiResponse.success("Account created", response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AccountResponse>> getById(@PathVariable Long id) {
        AccountResponse response = accountService.getById(id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/{id}/transactions")
    public ResponseEntity<ApiResponse<List<TransactionResponse>>> transactions(@PathVariable Long id) {
        List<TransactionResponse> payload = transactionService.findByAccount(id);
        return ResponseEntity.ok(ApiResponse.success(payload));
    }
}