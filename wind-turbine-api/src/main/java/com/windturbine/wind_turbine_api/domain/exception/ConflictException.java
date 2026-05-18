package com.windturbine.wind_turbine_api.domain.exception;

/**
 * Thrown when an operation collides with existing state —
 * duplicate email, duplicate username, etc.
 * The advice translates this to HTTP 409 Conflict.
 */
public class ConflictException extends DomainException {

    public ConflictException(String message) {
        super(message);
    }
}
