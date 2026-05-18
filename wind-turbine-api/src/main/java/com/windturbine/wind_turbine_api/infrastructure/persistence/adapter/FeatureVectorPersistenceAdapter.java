package com.windturbine.wind_turbine_api.infrastructure.persistence.adapter;

import com.windturbine.wind_turbine_api.domain.model.FeatureVector;
import com.windturbine.wind_turbine_api.domain.port.out.FeatureVectorRepositoryPort;
import com.windturbine.wind_turbine_api.infrastructure.persistence.mapper.FeatureVectorPersistenceMapper;
import com.windturbine.wind_turbine_api.infrastructure.persistence.repository.FeatureVectorJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FeatureVectorPersistenceAdapter implements FeatureVectorRepositoryPort {

    private final FeatureVectorJpaRepository featureVectorJpaRepository;
    private final FeatureVectorPersistenceMapper mapper;

    @Override
    public List<FeatureVector> findLatest(int limit) {
        return mapper.toDomainList(
                featureVectorJpaRepository.findLatest(PageRequest.of(0, limit))
        );
    }

    @Override
    public List<FeatureVector> findByRange(Instant start, Instant end, String scenario) {
        return mapper.toDomainList(
                featureVectorJpaRepository.findByRange(start, end, scenario)
        );
    }
}
