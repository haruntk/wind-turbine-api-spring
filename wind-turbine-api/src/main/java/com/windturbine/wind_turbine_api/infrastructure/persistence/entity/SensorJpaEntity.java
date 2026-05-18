package com.windturbine.wind_turbine_api.infrastructure.persistence.entity;

import com.windturbine.wind_turbine_api.domain.enums.SensorType;
import com.windturbine.wind_turbine_api.domain.enums.WindowGroup;
import com.windturbine.wind_turbine_api.infrastructure.persistence.converter.SensorTypeConverter;
import com.windturbine.wind_turbine_api.infrastructure.persistence.converter.WindowGroupConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * JPA persistence representation of a Sensor channel.
 * Maps to the 'sensors' table.
 */
@Entity
@Table(name = "sensors", uniqueConstraints = {
        @UniqueConstraint(name = "uq_sensors_channel_name", columnNames = "channel_name")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SensorJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sensor_id")
    private Long sensorId;

    @Convert(converter = SensorTypeConverter.class)
    @Column(name = "sensor_type", nullable = false)
    private SensorType sensorType;

    @Column(name = "channel_name", nullable = false)
    private String channelName;

    @Column(name = "location")
    private String location;

    @Column(name = "axis")
    private String axis;

    @Column(name = "sampling_freq_hz", nullable = false)
    private double samplingFreqHz;

    @Convert(converter = WindowGroupConverter.class)
    @Column(name = "window_group", nullable = false)
    private WindowGroup windowGroup;

    @Column(name = "window_seconds", nullable = false)
    private double windowSeconds;

    @Column(name = "feature_count", nullable = false)
    private int featureCount;
}
