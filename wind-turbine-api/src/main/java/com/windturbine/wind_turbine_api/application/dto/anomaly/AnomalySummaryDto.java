package com.windturbine.wind_turbine_api.application.dto.anomaly;

/**
 * Aggregated anomaly statistics grouped by severity —
 * returned by the sp_anomaly_summary stored procedure.
 */
public record AnomalySummaryDto(
        String severity,
        long totalCount,
        double avgScore,
        double maxScore
) {
}
