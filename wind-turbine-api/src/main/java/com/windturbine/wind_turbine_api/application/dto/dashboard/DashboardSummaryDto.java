package com.windturbine.wind_turbine_api.application.dto.dashboard;

import com.windturbine.wind_turbine_api.application.dto.anomaly.AnomalySummaryDto;

import java.util.List;

public record DashboardSummaryDto(
        List<AnomalySummaryDto> anomalySummaries,
        ModelStatsDto modelStats
) {
}
