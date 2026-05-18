package com.windturbine.wind_turbine_api.domain.port.out;

import com.windturbine.wind_turbine_api.domain.model.AnomalyResult;

import java.time.Instant;
import java.util.List;

/**
 * Outbound port for AnomalyResult hypertable queries.
 */
public interface AnomalyResultRepositoryPort {

    List<AnomalyResult> findByRange(Instant start, Instant end);

    List<AnomalyResult> findLatest(int limit);
}
