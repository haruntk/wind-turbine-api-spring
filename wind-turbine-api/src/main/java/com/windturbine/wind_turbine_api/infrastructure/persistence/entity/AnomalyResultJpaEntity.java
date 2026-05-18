package com.windturbine.wind_turbine_api.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

import java.time.Instant;

/**
 * JPA persistence representation of an AnomalyResult row.
 * Maps to the 'anomaly_results' TimescaleDB hypertable.
 */
@Entity
@Immutable
@Table(name = "anomaly_results")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnomalyResultJpaEntity {

    @Id
    @Column(name = "time", nullable = false)
    private Instant time;

    @Column(name = "reconstruction_error", nullable = false)
    private double reconstructionError;

    @Column(name = "threshold", nullable = false)
    private double threshold;

    @Column(name = "is_anomaly", nullable = false)
    private boolean anomaly;

    @Column(name = "model_version", nullable = false)
    private String modelVersion;
}
