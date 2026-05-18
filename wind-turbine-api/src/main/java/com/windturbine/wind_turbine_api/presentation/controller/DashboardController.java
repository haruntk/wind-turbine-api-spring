package com.windturbine.wind_turbine_api.presentation.controller;

import com.windturbine.wind_turbine_api.application.dto.anomaly.AnomalySummaryDto;
import com.windturbine.wind_turbine_api.application.dto.dashboard.DashboardSummaryDto;
import com.windturbine.wind_turbine_api.application.dto.dashboard.ModelStatsDto;
import com.windturbine.wind_turbine_api.application.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('VIEWER', 'OPERATOR', 'ADMIN')")
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/summary")
    public DashboardSummaryDto getSummary(@RequestParam(required = false) Instant start,
                                          @RequestParam(required = false) Instant end) {
        List<AnomalySummaryDto> anomalySummaries = dashboardService.getAnomalySummary(start, end);
        ModelStatsDto modelStats = dashboardService.getModelStats(start, end);
        return new DashboardSummaryDto(anomalySummaries, modelStats);
    }

    @GetMapping("/model-stats")
    public ModelStatsDto getModelStats(@RequestParam(required = false) Instant start,
                                       @RequestParam(required = false) Instant end) {
        return dashboardService.getModelStats(start, end);
    }
}
