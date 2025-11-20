package com.movieticketing.backend.service;

import com.movieticketing.backend.dto.request.CreateAccountRequest;
import com.movieticketing.backend.dto.response.AccountResponse;
import com.movieticketing.backend.entity.Account;
import com.movieticketing.backend.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository repository;

    @InjectMocks
    private AccountService service;

    private Account saved;

    @BeforeEach
    void setUp() {
        saved = Account.builder()
                .id(1L)
                .firstName("Haroon")
                .lastName("Butt")
                .username("haroon")
                .password("secret")
                .rememberMe(true)
                .build();
    }

    @Test
    void createBuildsEntityAndReturnsResponse() {
        when(repository.save(any(Account.class))).thenReturn(saved);

        CreateAccountRequest request = CreateAccountRequest.builder()
                .firstName("Haroon")
                .lastName("Butt")
                .username("haroon")
                .password("secret")
                .rememberMe(true)
                .build();

        AccountResponse response = service.create(request);
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getUsername()).isEqualTo("haroon");
    }

    @Test
    void getByIdDelegatesToRepository() {
        when(repository.findById(1L)).thenReturn(java.util.Optional.of(saved));

        AccountResponse response = service.getById(1L);
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
    }
}