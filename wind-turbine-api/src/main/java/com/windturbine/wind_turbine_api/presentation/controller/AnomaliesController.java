package com.windturbine.wind_turbine_api.presentation.controller;

import com.windturbine.wind_turbine_api.application.dto.anomaly.AnomalyLogDto;
import com.windturbine.wind_turbine_api.application.dto.anomaly.AnomalyResultDto;
import com.windturbine.wind_turbine_api.application.service.AnomalyService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;

/**
 * Class-level @PreAuthorize sets a default; method-level overrides it.
 */
@RestController
@RequestMapping("/api/anomalies")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('VIEWER', 'OPERATOR', 'ADMIN')")
public class AnomaliesController {

    private final AnomalyService anomalyService;

    @GetMapping("/results")
    @PreAuthorize("hasAnyRole('OPERATOR', 'ADMIN')")
    public List<AnomalyResultDto> getResults(@RequestParam Instant start,
                                             @RequestParam Instant end) {
        return anomalyService.getResultsByRange(start, end);
    }

    @GetMapping("/results/latest")
    @PreAuthorize("hasAnyRole('OPERATOR', 'ADMIN')")
    public List<AnomalyResultDto> getLatestResults(@RequestParam(defaultValue = "20") int limit) {
        return anomalyService.getLatestResults(limit);
    }

    @GetMapping("/logs")
    public List<AnomalyLogDto> getLogs(@RequestParam(required = false) String severity,
                                       @RequestParam(required = false) Instant start,
                                       @RequestParam(required = false) Instant end) {
        return anomalyService.getLogs(severity, start, end);
    }

    @GetMapping("/logs/{id}")
    public AnomalyLogDto getLogById(@PathVariable Long id) {
        return anomalyService.getLogById(id);
    }
}
