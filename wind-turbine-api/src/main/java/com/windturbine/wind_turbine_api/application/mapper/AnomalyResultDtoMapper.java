package com.windturbine.wind_turbine_api.application.mapper;

import com.windturbine.wind_turbine_api.application.dto.anomaly.AnomalyResultDto;
import com.windturbine.wind_turbine_api.domain.model.AnomalyResult;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Domain AnomalyResult -> AnomalyResultDto.
 * Names match, including the boolean {@code anomaly}.
 */
@Mapper(componentModel = "spring")
public interface AnomalyResultDtoMapper {

    AnomalyResultDto toDto(AnomalyResult source);

    List<AnomalyResultDto> toDtoList(List<AnomalyResult> sources);
}
