package com.windturbine.wind_turbine_api.infrastructure.persistence.mapper;

import com.windturbine.wind_turbine_api.domain.model.AnomalyResult;
import com.windturbine.wind_turbine_api.infrastructure.persistence.entity.AnomalyResultJpaEntity;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Read-only mapper — AnomalyResult hypertable salt-okunur.
 */
@Mapper(componentModel = "spring")
public interface AnomalyResultPersistenceMapper {

    default AnomalyResult toDomain(AnomalyResultJpaEntity jpa) {
        if (jpa == null) {
            return null;
        }
        return AnomalyResult.rehydrate(
                jpa.getTime(),
                jpa.getReconstructionError(),
                jpa.getThreshold(),
                jpa.isAnomaly(),
                jpa.getModelVersion()
        );
    }

    default List<AnomalyResult> toDomainList(List<AnomalyResultJpaEntity> entities) {
        return entities.stream().map(this::toDomain).toList();
    }
}
