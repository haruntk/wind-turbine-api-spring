package com.windturbine.wind_turbine_api.presentation.controller;

import com.windturbine.wind_turbine_api.application.dto.sensor.SensorDto;
import com.windturbine.wind_turbine_api.application.service.SensorService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/sensors")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('VIEWER', 'OPERATOR', 'ADMIN')")
public class SensorsController {

    private final SensorService sensorService;

    @GetMapping
    public List<SensorDto> getAll() {
        return sensorService.findAll();
    }

    @GetMapping("/{id}")
    public SensorDto getById(@PathVariable Long id) {
        return sensorService.findById(id);
    }
}
