package com.windturbine.wind_turbine_api.infrastructure.persistence.mapper;

import com.windturbine.wind_turbine_api.domain.model.FeatureVector;
import com.windturbine.wind_turbine_api.infrastructure.persistence.entity.FeatureVectorJpaEntity;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Read-only mapper — FeatureVector hypertable salt-okunur.
 */
@Mapper(componentModel = "spring")
public interface FeatureVectorPersistenceMapper {

    default FeatureVector toDomain(FeatureVectorJpaEntity jpa) {
        if (jpa == null) {
            return null;
        }
        return FeatureVector.rehydrate(
                jpa.getTime(),
                jpa.getScenarioLabel(),
                jpa.getFeatures()
        );
    }

    default List<FeatureVector> toDomainList(List<FeatureVectorJpaEntity> entities) {
        return entities.stream().map(this::toDomain).toList();
    }
}
