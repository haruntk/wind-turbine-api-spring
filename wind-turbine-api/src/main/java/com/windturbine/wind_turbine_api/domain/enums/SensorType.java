package com.windturbine.wind_turbine_api.domain.enums;

/**
 * Types of sensors installed on the wind turbine.
 * Maps to the sensor_type CHECK constraint in the database.
 */
public enum SensorType {
    ACCELEROMETER,
    TEMPERATURE,
    TACHOMETER,
    ANEMOMETER,
    WIND_VANE
}
