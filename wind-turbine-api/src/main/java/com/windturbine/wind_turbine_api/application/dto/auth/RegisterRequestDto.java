package com.windturbine.wind_turbine_api.application.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterRequestDto(

        @NotBlank(message = "Username is required.")
        @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters.")
        String username,

        @NotBlank(message = "Email is required.")
        @Email(message = "A valid email address is required.")
        String email,

        // Composite rule: 8+ chars, at least one uppercase, lowercase, and digit.
        // Combined into a single positive-lookahead regex — equivalent to the four
        // separate FluentValidation rules from the .NET source.
        @NotBlank(message = "Password is required.")
        @Pattern(
                regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).{8,}$",
                message = "Password must be at least 8 characters and contain an uppercase letter, a lowercase letter, and a digit."
        )
        String password
) {
}
