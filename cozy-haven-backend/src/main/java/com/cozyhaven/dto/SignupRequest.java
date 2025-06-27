package com.cozyhaven.dto;

import jakarta.validation.constraints.*;

import java.util.Set;

public record SignupRequest(
        @NotBlank String username,
        @NotBlank @Email String email,
        @NotBlank @Size(min = 6) String password,
        Set<String> roles
) {}
