package com.windturbine.wind_turbine_api.application.dto.auth;

import java.time.Instant;

public record AuthResponseDto(
        String token,
        String username,
        String email,
        String role,
        Instant expiresAt
) {
}
