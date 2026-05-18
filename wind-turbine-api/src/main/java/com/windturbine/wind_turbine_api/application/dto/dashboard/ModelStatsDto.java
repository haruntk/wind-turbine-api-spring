package com.windturbine.wind_turbine_api.application.dto.dashboard;

/**
 * LSTM model performance statistics —
 * returned by the sp_model_stats stored procedure.
 */
public record ModelStatsDto(
        long totalInferences,
        long anomalyCount,
        long normalCount,
        double avgError,
        double maxError,
        double anomalyRate
) {
}
