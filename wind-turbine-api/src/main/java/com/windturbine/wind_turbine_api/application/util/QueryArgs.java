package com.windturbine.wind_turbine_api.application.util;

import java.time.Instant;

/**
 * Shared validators for query-string arguments.
 */
public final class QueryArgs {

    public static final int MAX_LIMIT = 1000;

    private QueryArgs() {}

    public static void requireValidRange(Instant start, Instant end) {
        if (start == null || end == null || !start.isBefore(end)) {
            throw new IllegalArgumentException("Start date must be before end date.");
        }
    }

    public static void requireValidLimit(int limit) {
        if (limit < 1 || limit > MAX_LIMIT) {
            throw new IllegalArgumentException("Limit must be between 1 and " + MAX_LIMIT + ".");
        }
    }
}
