package com.windturbine.wind_turbine_api.application.dto.anomaly;

import java.time.Instant;

public record AnomalyResultDto(
        Instant time,
        double reconstructionError,
        double threshold,
        boolean anomaly,
        String modelVersion
) {
}
