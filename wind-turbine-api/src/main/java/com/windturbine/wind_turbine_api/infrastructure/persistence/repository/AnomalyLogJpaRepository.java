package com.windturbine.wind_turbine_api.infrastructure.persistence.repository;

import com.windturbine.wind_turbine_api.domain.enums.Severity;
import com.windturbine.wind_turbine_api.infrastructure.persistence.entity.AnomalyLogJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;

public interface AnomalyLogJpaRepository extends JpaRepository<AnomalyLogJpaEntity, Long> {

    /**
     * All filter parameters are optional ” a NULL means "ignore this filter".
     */
    @Query("""
            SELECT a FROM AnomalyLogJpaEntity a
            WHERE (:severity IS NULL OR a.severity = :severity)
              AND (CAST(:start AS timestamp) IS NULL OR a.time >= :start)
              AND (CAST(:end   AS timestamp) IS NULL OR a.time <  :end)
            ORDER BY a.time DESC
            """)
    List<AnomalyLogJpaEntity> findByFilter(@Param("severity") Severity severity,
                                           @Param("start") Instant start,
                                           @Param("end") Instant end);
}
