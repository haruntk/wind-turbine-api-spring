package com.windturbine.wind_turbine_api.domain.exception;

/**
 * Thrown when a requested entity is not found in the data store.
 */
public class NotFoundException extends DomainException {

    public NotFoundException(String entityName, Object key) {
        super("Entity '" + entityName + "' with key '" + key + "' was not found.");
    }
}
