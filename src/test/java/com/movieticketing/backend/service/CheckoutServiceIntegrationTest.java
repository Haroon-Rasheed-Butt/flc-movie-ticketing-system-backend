package com.movieticketing.backend.service;

import com.movieticketing.backend.dto.request.CheckoutRequest;
import com.movieticketing.backend.dto.response.CheckoutResponse;
import com.movieticketing.backend.entity.Account;
import com.movieticketing.backend.entity.CartItem;
import com.movieticketing.backend.entity.Movie;
import com.movieticketing.backend.entity.MovieType;
import com.movieticketing.backend.repository.AccountRepository;
import com.movieticketing.backend.repository.CartItemRepository;
import com.movieticketing.backend.repository.MovieRepository;
import com.movieticketing.backend.repository.MovieTypeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CheckoutServiceIntegrationTest {

    @Container
    static final MySQLContainer<?> MYSQL = new MySQLContainer<>("mysql:8.0")
            .withDatabaseName("movie_ticketing")
            .withUsername("admin")
            .withPassword("admin");

    @DynamicPropertySource
    static void register(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", MYSQL::getJdbcUrl);
        registry.add("spring.datasource.username", MYSQL::getUsername);
        registry.add("spring.datasource.password", MYSQL::getPassword);
    }

    @Autowired
    private CheckoutService checkoutService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private MovieTypeRepository movieTypeRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Test
    void checkoutProcessesPendingCartItems() {
        Account account = accountRepository.save(Account.builder()
                .firstName("Test")
                .lastName("User")
                .username("test-user")
                .password("secret")
                .rememberMe(false)
                .build());

        MovieType type = movieTypeRepository.save(MovieType.builder()
                .name("Test Type")
                .build());

        Movie movie = movieRepository.save(Movie.builder()
                .name("Integration Movie")
                .type(type)
                .ticketPrice(new BigDecimal("15.00"))
                .owner(account)
                .build());

        CartItem entry = cartItemRepository.save(CartItem.builder()
                .account(account)
                .movie(movie)
                .quantity(2)
                .purchased(false)
                .build());

        CheckoutRequest request = CheckoutRequest.builder()
                .accountId(account.getId())
                .cartItemIds(List.of(entry.getId()))
                .build();

        CheckoutResponse response = checkoutService.checkout(request);

        assertThat(response).isNotNull();
        assertThat(response.getTotalAmount()).isEqualTo(new BigDecimal("30.00"));
        CartItem purchased = cartItemRepository.findById(entry.getId()).orElseThrow();
        assertThat(purchased.getPurchased()).isTrue();
        assertThat(purchased.getPurchaseDate()).isNotNull();
    }
}