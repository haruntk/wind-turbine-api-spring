package com.windturbine.wind_turbine_api.infrastructure.persistence.repository;

import com.windturbine.wind_turbine_api.infrastructure.persistence.entity.FeatureVectorJpaEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;

public interface FeatureVectorJpaRepository extends JpaRepository<FeatureVectorJpaEntity, Instant> {

    /**
     * Latest N rows ordered by time descending caller passes a {@code Pageable}
     * such as {@code PageRequest.of(0, limit)}.
     */
    @Query("SELECT fv FROM FeatureVectorJpaEntity fv ORDER BY fv.time DESC")
    List<FeatureVectorJpaEntity> findLatest(Pageable pageable);

    /**
     * Time-range query with an optional scenario filter.
     * The {@code :scenario IS NULL} guard makes the parameter effectively optional.
     */
    @Query("""
            SELECT fv FROM FeatureVectorJpaEntity fv
            WHERE fv.time >= :start
              AND fv.time <  :end
              AND (:scenario IS NULL OR fv.scenarioLabel = :scenario)
            ORDER BY fv.time ASC
            """)
    List<FeatureVectorJpaEntity> findByRange(@Param("start") Instant start,
                                             @Param("end") Instant end,
                                             @Param("scenario") String scenario);
}
