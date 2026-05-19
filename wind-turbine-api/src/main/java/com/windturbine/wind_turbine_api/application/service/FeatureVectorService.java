package com.windturbine.wind_turbine_api.application.service;

import com.windturbine.wind_turbine_api.application.dto.feature.FeatureVectorDto;
import com.windturbine.wind_turbine_api.application.mapper.FeatureVectorDtoMapper;
import com.windturbine.wind_turbine_api.application.util.QueryArgs;
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

    private final FeatureVectorRepositoryPort featureVectorRepositoryPort;
    private final FeatureVectorDtoMapper featureVectorDtoMapper;

    public List<FeatureVectorDto> getLatest(int limit) {
        QueryArgs.requireValidLimit(limit);
        return featureVectorDtoMapper.toDtoList(featureVectorRepositoryPort.findLatest(limit));
    }

    public List<FeatureVectorDto> getByRange(Instant start, Instant end, String scenario) {
        QueryArgs.requireValidRange(start, end);
        return featureVectorDtoMapper.toDtoList(
                featureVectorRepositoryPort.findByRange(start, end, scenario)
        );
    }
}
