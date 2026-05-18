package com.windturbine.wind_turbine_api.infrastructure.persistence.adapter;

import com.windturbine.wind_turbine_api.domain.model.AnomalyResult;
import com.windturbine.wind_turbine_api.domain.port.out.AnomalyResultRepositoryPort;
import com.windturbine.wind_turbine_api.infrastructure.persistence.mapper.AnomalyResultPersistenceMapper;
import com.windturbine.wind_turbine_api.infrastructure.persistence.repository.AnomalyResultJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AnomalyResultPersistenceAdapter implements AnomalyResultRepositoryPort {

    private final AnomalyResultJpaRepository anomalyResultJpaRepository;
    private final AnomalyResultPersistenceMapper mapper;

    @Override
    public List<AnomalyResult> findByRange(Instant start, Instant end) {
        return mapper.toDomainList(
                anomalyResultJpaRepository.findByRange(start, end)
        );
    }

    @Override
    public List<AnomalyResult> findLatest(int limit) {
        return mapper.toDomainList(
                anomalyResultJpaRepository.findLatest(PageRequest.of(0, limit))
        );
    }
}
