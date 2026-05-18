package com.windturbine.wind_turbine_api.domain.port.out;

import com.windturbine.wind_turbine_api.domain.model.Sensor;

import java.util.List;
import java.util.Optional;

/**
 * Outbound port for Sensor metadata operations.
 */
public interface SensorRepositoryPort {

    Optional<Sensor> findById(Long sensorId);

    Optional<Sensor> findByChannelName(String channelName);

    List<Sensor> findAll();
}
