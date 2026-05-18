package com.windturbine.wind_turbine_api.application.service;

import com.windturbine.wind_turbine_api.application.dto.feature.FeatureVectorDto;
import com.windturbine.wind_turbine_api.application.mapper.FeatureVectorDtoMapper;
import com.windturbine.wind_turbine_api.domain.port.out.FeatureVectorRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FeatureVectorService {

    private static final int MAX_LIMIT = 1000;

    private final FeatureVectorRepositoryPort featureVectorRepositoryPort;
    private final FeatureVectorDtoMapper featureVectorDtoMapper;

    public List<FeatureVectorDto> getLatest(int limit) {
        if (limit < 1 || limit > MAX_LIMIT) {
            throw new IllegalArgumentException("Limit must be between 1 and " + MAX_LIMIT + ".");
        }
        return featureVectorDtoMapper.toDtoList(featureVectorRepositoryPort.findLatest(limit));
    }

    public List<FeatureVectorDto> getByRange(Instant start, Instant end, String scenario) {
        requireValidRange(start, end);
        return featureVectorDtoMapper.toDtoList(
                featureVectorRepositoryPort.findByRange(start, end, scenario)
        );
    }

    private static void requireValidRange(Instant start, Instant end) {
        if (start == null || end == null || !start.isBefore(end)) {
            throw new IllegalArgumentException("Start date must be before end date.");
        }
    }
}
