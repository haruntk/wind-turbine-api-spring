package com.windturbine.wind_turbine_api.application.dto.feature;

import java.time.Instant;

public record FeatureVectorDto(
        Instant time,
        String scenarioLabel,
        double[] features
) {
}
