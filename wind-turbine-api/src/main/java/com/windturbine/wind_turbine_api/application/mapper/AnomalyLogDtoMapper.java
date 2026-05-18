package com.windturbine.wind_turbine_api.application.mapper;

import com.windturbine.wind_turbine_api.application.dto.anomaly.AnomalyLogDto;
import com.windturbine.wind_turbine_api.domain.enums.Severity;
import com.windturbine.wind_turbine_api.domain.model.AnomalyLog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

/**
 * Domain AnomalyLog -> AnomalyLogDto.
 * Severity is exposed as an uppercase string in the API response.
 */
@Mapper(componentModel = "spring")
public interface AnomalyLogDtoMapper {

    @Mapping(source = "severity", target = "severity", qualifiedByName = "severityToString")
    AnomalyLogDto toDto(AnomalyLog source);

    List<AnomalyLogDto> toDtoList(List<AnomalyLog> sources);

    @Named("severityToString")
    default String severityToString(Severity severity) {
        return severity == null ? null : severity.name();
    }
}
