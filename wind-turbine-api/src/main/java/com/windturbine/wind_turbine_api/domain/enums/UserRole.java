package com.windturbine.wind_turbine_api.domain.enums;

/**
 * System user roles for authorization.
 * Hierarchical: ADMIN > OPERATOR > VIEWER.
 */
public enum UserRole {
    VIEWER,
    OPERATOR,
    ADMIN
}
