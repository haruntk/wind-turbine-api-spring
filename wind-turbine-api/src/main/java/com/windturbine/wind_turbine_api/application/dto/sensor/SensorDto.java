package com.windturbine.wind_turbine_api.application.dto.sensor;

public record SensorDto(
        Long sensorId,
        String sensorType,
        String channelName,
        String location,
        String axis,
        double samplingFreqHz,
        String windowGroup,
        double windowSeconds,
        int featureCount
) {
}
