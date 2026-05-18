package com.windturbine.wind_turbine_api.infrastructure.persistence.mapper;

import com.windturbine.wind_turbine_api.domain.model.AnomalyLog;
import com.windturbine.wind_turbine_api.infrastructure.persistence.entity.AnomalyLogJpaEntity;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Read-only mapper — AnomalyLog satırları DB trigger'ı tarafından üretiliyor,
 * uygulama tarafından yazılmıyor.
 */
@Mapper(componentModel = "spring")
public interface AnomalyLogPersistenceMapper {

    default AnomalyLog toDomain(AnomalyLogJpaEntity jpa) {
        if (jpa == null) {
            return null;
        }
        return AnomalyLog.rehydrate(
                jpa.getLogId(),
                jpa.getTime(),
                jpa.getReconstructionError(),
                jpa.getThreshold(),
                jpa.getSeverity(),
                jpa.getCreatedAt()
        );
    }

    default List<AnomalyLog> toDomainList(List<AnomalyLogJpaEntity> entities) {
        return entities.stream().map(this::toDomain).toList();
    }
}
