package com.windturbine.wind_turbine_api.application.mapper;

import com.windturbine.wind_turbine_api.application.dto.sensor.SensorDto;
import com.windturbine.wind_turbine_api.domain.model.Sensor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

/**
 * Domain Sensor -> SensorDto.
 * Enum values are exposed as lowercase strings in the API response
 * (e.g. WIND_VANE -> "wind_vane") — matches the .NET wire format.
 */
@Mapper(componentModel = "spring")
public interface SensorDtoMapper {

    @Mapping(source = "sensorType", target = "sensorType", qualifiedByName = "enumToLowercase")
    @Mapping(source = "windowGroup", target = "windowGroup", qualifiedByName = "enumToLowercase")
    SensorDto toDto(Sensor sensor);

    List<SensorDto> toDtoList(List<Sensor> sensors);

    @Named("enumToLowercase")
    default String enumToLowercase(Enum<?> value) {
        return value == null ? null : value.name().toLowerCase();
    }
}
