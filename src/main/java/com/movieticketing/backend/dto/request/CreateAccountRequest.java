package com.movieticketing.backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateAccountRequest {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Builder.Default
    private Boolean rememberMe = false;

    @NotBlank
    private String username;

    @NotBlank
    private String password;
}