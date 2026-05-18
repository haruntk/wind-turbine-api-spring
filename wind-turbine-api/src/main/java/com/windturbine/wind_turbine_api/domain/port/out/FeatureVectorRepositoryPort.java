package com.windturbine.wind_turbine_api.domain.port.out;

import com.windturbine.wind_turbine_api.domain.model.FeatureVector;

import java.time.Instant;
import java.util.List;

/**
 * Outbound port for FeatureVector hypertable queries.
 */
public interface FeatureVectorRepositoryPort {

    List<FeatureVector> findLatest(int limit);

    List<FeatureVector> findByRange(Instant start, Instant end, String scenario);
}
