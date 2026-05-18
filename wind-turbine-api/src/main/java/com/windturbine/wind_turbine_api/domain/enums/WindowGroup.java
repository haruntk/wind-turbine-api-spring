package com.windturbine.wind_turbine_api.domain.enums;

/**
 * Sensor window grouping categories for FFT processing.
 * Maps to the window_group CHECK constraint in the database.
 */
public enum WindowGroup {
    BEARING,
    NACELLE,
    TOWER,
    SLOW
}
