package com.windturbine.wind_turbine_api.application.service;

import com.windturbine.wind_turbine_api.application.dto.sensor.SensorDto;
import com.windturbine.wind_turbine_api.application.mapper.SensorDtoMapper;
import com.windturbine.wind_turbine_api.domain.exception.NotFoundException;
import com.windturbine.wind_turbine_api.domain.model.Sensor;
import com.windturbine.wind_turbine_api.domain.port.out.SensorRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SensorService {

    private final SensorRepositoryPort sensorRepositoryPort;
    private final SensorDtoMapper sensorDtoMapper;

    public List<SensorDto> findAll() {
        return sensorDtoMapper.toDtoList(sensorRepositoryPort.findAll());
    }

    public SensorDto findById(Long sensorId) {
        Sensor sensor = sensorRepositoryPort.findById(sensorId)
                .orElseThrow(() -> new NotFoundException("Sensor", sensorId));
        return sensorDtoMapper.toDto(sensor);
    }
}
