package com.windturbine.wind_turbine_api.infrastructure.persistence.repository;

import com.windturbine.wind_turbine_api.infrastructure.persistence.entity.AnomalyResultJpaEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;

public interface AnomalyResultJpaRepository extends JpaRepository<AnomalyResultJpaEntity, Instant> {

    @Query("""
            SELECT a FROM AnomalyResultJpaEntity a
            WHERE a.time >= :start AND a.time < :end
            ORDER BY a.time ASC
            """)
    List<AnomalyResultJpaEntity> findByRange(@Param("start") Instant start,
                                             @Param("end") Instant end);

    @Query("SELECT a FROM AnomalyResultJpaEntity a ORDER BY a.time DESC")
    List<AnomalyResultJpaEntity> findLatest(Pageable pageable);
}
