package com.windturbine.wind_turbine_api.application.service;

import com.windturbine.wind_turbine_api.application.dto.anomaly.AnomalySummaryDto;
import com.windturbine.wind_turbine_api.application.dto.dashboard.ModelStatsDto;
import com.windturbine.wind_turbine_api.application.port.DashboardQueryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

/**
 * Aggregated anomaly and model performance statistics for the dashboard.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DashboardService {

    private static final Duration DEFAULT_LOOKBACK = Duration.ofHours(24);

    private final DashboardQueryPort dashboardQueryPort;

    public List<AnomalySummaryDto> getAnomalySummary(Instant start, Instant end) {
        Instant effectiveEnd = end != null ? end : Instant.now();
        Instant effectiveStart = start != null ? start : effectiveEnd.minus(DEFAULT_LOOKBACK);
        return dashboardQueryPort.getAnomalySummary(effectiveStart, effectiveEnd);
    }

    public ModelStatsDto getModelStats(Instant start, Instant end) {
        Instant effectiveEnd = end != null ? end : Instant.now();
        Instant effectiveStart = start != null ? start : effectiveEnd.minus(DEFAULT_LOOKBACK);
        return dashboardQueryPort.getModelStats(effectiveStart, effectiveEnd);
    }
}
