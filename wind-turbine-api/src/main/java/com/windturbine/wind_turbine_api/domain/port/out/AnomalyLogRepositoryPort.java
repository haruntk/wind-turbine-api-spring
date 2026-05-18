package com.windturbine.wind_turbine_api.domain.port.out;

import com.windturbine.wind_turbine_api.domain.enums.Severity;
import com.windturbine.wind_turbine_api.domain.model.AnomalyLog;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * Outbound port for AnomalyLog alert operations.
 */
public interface AnomalyLogRepositoryPort {

    Optional<AnomalyLog> findById(Long logId);

    List<AnomalyLog> findByFilter(Severity severity, Instant start, Instant end);
}
