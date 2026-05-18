package com.windturbine.wind_turbine_api.application.dto.anomaly;

import java.time.Instant;

public record AnomalyLogDto(
        Long logId,
        Instant time,
        double reconstructionError,
        double threshold,
        String severity,
        Instant createdAt
) {
}
