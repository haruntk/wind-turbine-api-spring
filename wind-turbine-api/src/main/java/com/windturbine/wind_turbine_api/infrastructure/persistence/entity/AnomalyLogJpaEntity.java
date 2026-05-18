package com.windturbine.wind_turbine_api.infrastructure.persistence.entity;

import com.windturbine.wind_turbine_api.domain.enums.Severity;
import com.windturbine.wind_turbine_api.infrastructure.persistence.converter.SeverityConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

/**
 * JPA persistence representation of an AnomalyLog alert.
 * Maps to the 'anomaly_logs' table (a regular Postgres table, not a hypertable).
 */
@Entity
@Table(name = "anomaly_logs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnomalyLogJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id")
    private Long logId;

    @Column(name = "time", nullable = false)
    private Instant time;

    @Column(name = "reconstruction_error", nullable = false)
    private double reconstructionError;

    @Column(name = "threshold", nullable = false)
    private double threshold;

    @Convert(converter = SeverityConverter.class)
    @Column(name = "severity", nullable = false)
    private Severity severity;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;
}
