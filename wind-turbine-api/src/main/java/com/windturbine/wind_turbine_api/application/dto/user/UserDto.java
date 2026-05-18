package com.windturbine.wind_turbine_api.application.dto.user;

import java.time.Instant;

/**
 * User response DTO — never exposes password hash.
 */
public record UserDto(
        Long userId,
        String username,
        String email,
        String roleName,
        boolean active,
        Instant createdAt,
        Instant lastLogin
) {
}
