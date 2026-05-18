package com.windturbine.wind_turbine_api.application.mapper;

import com.windturbine.wind_turbine_api.application.dto.feature.FeatureVectorDto;
import com.windturbine.wind_turbine_api.domain.model.FeatureVector;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Domain FeatureVector -> FeatureVectorDto.
 * Field names match one-to-one, MapStruct auto-generates.
 */
@Mapper(componentModel = "spring")
public interface FeatureVectorDtoMapper {

    FeatureVectorDto toDto(FeatureVector source);

    List<FeatureVectorDto> toDtoList(List<FeatureVector> sources);
}
