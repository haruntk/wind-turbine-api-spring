package com.windturbine.wind_turbine_api.infrastructure.security;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

/**
 * Type-safe binding of the {@code jwt.*} keys from application.yml.
 */
@ConfigurationProperties(prefix = "jwt")
@Validated
public record JwtProperties(

        @NotBlank
        @Size(min = 32, message = "JWT secret must be at least 32 characters for HMAC-SHA256.")
        String secretKey,

        @NotBlank
        String issuer,

        @NotBlank
        String audience,

        @Positive
        long expirationMinutes
) {
}
