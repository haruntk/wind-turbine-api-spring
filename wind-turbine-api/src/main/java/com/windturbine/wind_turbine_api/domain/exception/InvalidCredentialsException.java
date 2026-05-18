package com.windturbine.wind_turbine_api.domain.exception;

/**
 * Thrown when authentication fails — wrong email or wrong password.
 * The advice translates this to HTTP 401 Unauthorized.
 */
public class InvalidCredentialsException extends DomainException {

    public InvalidCredentialsException() {
        super("Invalid credentials.");
    }
}
