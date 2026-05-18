package com.windturbine.wind_turbine_api.domain.exception;

/**
 * Base exception for domain-level business rule violations.
 */
public class DomainException extends RuntimeException {

    public DomainException(String message) {
        super(message);
    }

    public DomainException(String message, Throwable cause) {
        super(message, cause);
    }
}
