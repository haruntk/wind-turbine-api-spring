package com.windturbine.wind_turbine_api.infrastructure.persistence.mapper;

import com.windturbine.wind_turbine_api.domain.model.Sensor;
import com.windturbine.wind_turbine_api.infrastructure.persistence.entity.SensorJpaEntity;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * MapStruct: Sensor JPA tarafından domain'e.
 * Port salt-okunur olduğu için toJpa metoduna gerek yok.
 */
@Mapper(componentModel = "spring")
public interface SensorPersistenceMapper {

    default Sensor toDomain(SensorJpaEntity jpa) {
        if (jpa == null) {
            return null;
        }
        return Sensor.rehydrate(
                jpa.getSensorId(),
                jpa.getSensorType(),
                jpa.getChannelName(),
                jpa.getLocation(),
                jpa.getAxis(),
                jpa.getSamplingFreqHz(),
                jpa.getWindowGroup(),
                jpa.getWindowSeconds(),
                jpa.getFeatureCount()
        );
    }

    default List<Sensor> toDomainList(List<SensorJpaEntity> entities) {
        return entities.stream().map(this::toDomain).toList();
    }
}
