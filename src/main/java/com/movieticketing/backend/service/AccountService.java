package com.movieticketing.backend.service;

import com.movieticketing.backend.dto.request.CreateAccountRequest;
import com.movieticketing.backend.dto.response.AccountResponse;
import com.movieticketing.backend.entity.Account;
import com.movieticketing.backend.exception.ResourceNotFoundException;
import com.movieticketing.backend.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountResponse create(CreateAccountRequest request) {
        Account account = Account.builder()
            .firstName(request.getFirstName())
            .lastName(request.getLastName())
            .rememberMe(Boolean.TRUE.equals(request.getRememberMe()))
            .username(request.getUsername())
            .password(request.getPassword())
            .build();

        return toResponse(accountRepository.save(account));
    }

    public AccountResponse getById(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account", id));

        return toResponse(account);
    }

    public Account findEntityById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account", id));
    }

    private AccountResponse toResponse(Account account) {
        return AccountResponse.builder()
                .id(account.getId())
                .firstName(account.getFirstName())
                .lastName(account.getLastName())
                .username(account.getUsername())
                .rememberMe(account.getRememberMe())
                .build();
    }
}