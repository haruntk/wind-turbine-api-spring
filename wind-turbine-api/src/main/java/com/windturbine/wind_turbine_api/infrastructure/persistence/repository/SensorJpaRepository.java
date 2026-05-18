package com.windturbine.wind_turbine_api.infrastructure.persistence.repository;

import com.windturbine.wind_turbine_api.infrastructure.persistence.entity.SensorJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SensorJpaRepository extends JpaRepository<SensorJpaEntity, Long> {

    Optional<SensorJpaEntity> findByChannelName(String channelName);
}
