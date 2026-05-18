package com.windturbine.wind_turbine_api.infrastructure.persistence.adapter;

import com.windturbine.wind_turbine_api.domain.enums.Severity;
import com.windturbine.wind_turbine_api.domain.model.AnomalyLog;
import com.windturbine.wind_turbine_api.domain.port.out.AnomalyLogRepositoryPort;
import com.windturbine.wind_turbine_api.infrastructure.persistence.mapper.AnomalyLogPersistenceMapper;
import com.windturbine.wind_turbine_api.infrastructure.persistence.repository.AnomalyLogJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AnomalyLogPersistenceAdapter implements AnomalyLogRepositoryPort {

    private final AnomalyLogJpaRepository anomalyLogJpaRepository;
    private final AnomalyLogPersistenceMapper mapper;

    @Override
    public Optional<AnomalyLog> findById(Long logId) {
        return anomalyLogJpaRepository.findById(logId).map(mapper::toDomain);
    }

    @Override
    public List<AnomalyLog> findByFilter(Severity severity, Instant start, Instant end) {
        return mapper.toDomainList(
                anomalyLogJpaRepository.findByFilter(severity, start, end)
        );
    }
}
