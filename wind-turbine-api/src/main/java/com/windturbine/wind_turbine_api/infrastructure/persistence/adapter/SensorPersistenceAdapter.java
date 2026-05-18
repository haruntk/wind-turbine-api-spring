package com.windturbine.wind_turbine_api.infrastructure.persistence.adapter;

import com.windturbine.wind_turbine_api.domain.model.Sensor;
import com.windturbine.wind_turbine_api.domain.port.out.SensorRepositoryPort;
import com.windturbine.wind_turbine_api.infrastructure.persistence.mapper.SensorPersistenceMapper;
import com.windturbine.wind_turbine_api.infrastructure.persistence.repository.SensorJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SensorPersistenceAdapter implements SensorRepositoryPort {

    private final SensorJpaRepository sensorJpaRepository;
    private final SensorPersistenceMapper mapper;

    @Override
    public Optional<Sensor> findById(Long sensorId) {
        return sensorJpaRepository.findById(sensorId).map(mapper::toDomain);
    }

    @Override
    public Optional<Sensor> findByChannelName(String channelName) {
        return sensorJpaRepository.findByChannelName(channelName).map(mapper::toDomain);
    }

    @Override
    public List<Sensor> findAll() {
        return mapper.toDomainList(sensorJpaRepository.findAll());
    }
}
