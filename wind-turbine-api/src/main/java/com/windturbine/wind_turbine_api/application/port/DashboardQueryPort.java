package com.windturbine.wind_turbine_api.application.port;

import com.windturbine.wind_turbine_api.application.dto.anomaly.AnomalySummaryDto;
import com.windturbine.wind_turbine_api.application.dto.dashboard.ModelStatsDto;

import java.time.Instant;
import java.util.List;

/**
 * Outbound port for DB-side aggregation queries
 */
public interface DashboardQueryPort {

    List<AnomalySummaryDto> getAnomalySummary(Instant start, Instant end);

    ModelStatsDto getModelStats(Instant start, Instant end);
}
