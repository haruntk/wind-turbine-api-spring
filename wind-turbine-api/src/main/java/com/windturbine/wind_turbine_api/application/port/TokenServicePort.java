package com.windturbine.wind_turbine_api.application.port;

import com.windturbine.wind_turbine_api.domain.model.User;

import java.time.Instant;

/**
 * Outbound port the application layer uses to produce JWT tokens.
 */
public interface TokenServicePort {

    GeneratedToken generateToken(User user, String roleName);

    record GeneratedToken(String token, Instant expiresAt) {
    }
}
