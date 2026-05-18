package com.windturbine.wind_turbine_api.presentation.exception;

import com.windturbine.wind_turbine_api.domain.exception.ConflictException;
import com.windturbine.wind_turbine_api.domain.exception.DomainException;
import com.windturbine.wind_turbine_api.domain.exception.InvalidCredentialsException;
import com.windturbine.wind_turbine_api.domain.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * Centralised exception-to-HTTP translator.
 * Replaces the .NET ExceptionHandlingMiddleware; returns RFC 7807
 * {@link ProblemDetail} responses with {@code application/problem+json}.
 * <p>
 * Returning ProblemDetail directly lets Spring set both the status code
 * and the content type automatically — no @ResponseStatus needed.
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidCredentialsException.class)
    public ProblemDetail handleInvalidCredentials(InvalidCredentialsException ex) {
        log.warn("Authentication failed: {}", ex.getMessage());
        return build(HttpStatus.UNAUTHORIZED, ex.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    public ProblemDetail handleNotFound(NotFoundException ex) {
        log.warn("Not found: {}", ex.getMessage());
        return build(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(ConflictException.class)
    public ProblemDetail handleConflict(ConflictException ex) {
        log.warn("Conflict: {}", ex.getMessage());
        return build(HttpStatus.CONFLICT, ex.getMessage());
    }

    /**
     * Catch-all for any other domain rule violation (validation of business
     * invariants thrown from inside domain entities, etc.).
     */
    @ExceptionHandler(DomainException.class)
    public ProblemDetail handleDomain(DomainException ex) {
        log.warn("Domain rule violated: {}", ex.getMessage());
        return build(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    /**
     * Triggered by {@code @Valid} on request bodies — surface every
     * field-level error in the response so clients can show form messages.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleBeanValidation(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
                .toList();
        ProblemDetail problem = build(HttpStatus.BAD_REQUEST, "Validation failed.");
        problem.setProperty("errors", errors);
        return problem;
    }

    /**
     * Argument constraint violations thrown from inside domain constructors
     * (e.g. {@code new User(blankUsername, ...)}).
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ProblemDetail handleIllegalArgument(IllegalArgumentException ex) {
        log.warn("Illegal argument: {}", ex.getMessage());
        return build(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGeneric(Exception ex) {
        log.error("Unhandled exception", ex);
        return build(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred.");
    }

    private static ProblemDetail build(HttpStatus status, String detail) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(status, detail);
        problem.setTitle(status.getReasonPhrase());
        return problem;
    }
}
