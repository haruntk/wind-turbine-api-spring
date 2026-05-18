package com.windturbine.wind_turbine_api.presentation.controller;

import com.windturbine.wind_turbine_api.application.dto.feature.FeatureVectorDto;
import com.windturbine.wind_turbine_api.application.service.FeatureVectorService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/features")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('OPERATOR', 'ADMIN')")
public class FeaturesController {

    private final FeatureVectorService featureVectorService;

    @GetMapping("/latest")
    public List<FeatureVectorDto> getLatest(@RequestParam(defaultValue = "20") int limit) {
        return featureVectorService.getLatest(limit);
    }

    /**
     * Time-range query.
     * Spring binds {@code Instant} parameters from ISO-8601 strings such as
     * {@code 2024-01-01T00:00:00Z}.
     */
    @GetMapping
    public List<FeatureVectorDto> getByRange(@RequestParam Instant start,
                                             @RequestParam Instant end,
                                             @RequestParam(required = false) String scenario) {
        return featureVectorService.getByRange(start, end, scenario);
    }
}
