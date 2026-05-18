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
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;

/**
 * JPA persistence representation of a FeatureVector row.
 * Maps to the 'feature_vectors' TimescaleDB hypertable.
 */
@Entity
@Immutable // Bu tablodaki veriler oluştuktan sonra asla güncellenmeyecek demektir. Dirty Checking iptali.
@Table(name = "feature_vectors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeatureVectorJpaEntity {

    @Id
    @Column(name = "time", nullable = false)
    private Instant time;

    @Column(name = "scenario_label")
    private String scenarioLabel;

    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(name = "features", columnDefinition = "double precision[]")
    private double[] features;
}
