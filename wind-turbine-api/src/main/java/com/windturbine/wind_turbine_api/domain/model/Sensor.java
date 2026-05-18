package com.windturbine.wind_turbine_api.domain.model;

import com.windturbine.wind_turbine_api.domain.enums.SensorType;
import com.windturbine.wind_turbine_api.domain.enums.WindowGroup;

import java.util.Objects;

/**
 * Sensor channel metadata — one of the 28 measurement channels on the turbine.
 */
public class Sensor {

    private Long sensorId;
    private SensorType sensorType;
    private String channelName;
    private String location;
    private String axis;
    private double samplingFreqHz;
    private WindowGroup windowGroup;
    private double windowSeconds;
    private int featureCount;

    private Sensor() {
        // For static rehydrate factory only.
    }

    public Sensor(SensorType sensorType,
                  String channelName,
                  double samplingFreqHz,
                  WindowGroup windowGroup,
                  double windowSeconds,
                  int featureCount,
                  String location,
                  String axis) {
        if (channelName == null || channelName.isBlank()) {
            throw new IllegalArgumentException("Channel name cannot be empty.");
        }
        if (samplingFreqHz <= 0) {
            throw new IllegalArgumentException("Sampling frequency must be positive.");
        }
        if (featureCount <= 0) {
            throw new IllegalArgumentException("Feature count must be positive.");
        }
        this.sensorType = sensorType;
        this.channelName = channelName;
        this.samplingFreqHz = samplingFreqHz;
        this.windowGroup = windowGroup;
        this.windowSeconds = windowSeconds;
        this.featureCount = featureCount;
        this.location = location;
        this.axis = axis;
    }

    public static Sensor rehydrate(Long sensorId,
                                   SensorType sensorType,
                                   String channelName,
                                   String location,
                                   String axis,
                                   double samplingFreqHz,
                                   WindowGroup windowGroup,
                                   double windowSeconds,
                                   int featureCount) {
        Sensor s = new Sensor();
        s.sensorId = sensorId;
        s.sensorType = sensorType;
        s.channelName = channelName;
        s.location = location;
        s.axis = axis;
        s.samplingFreqHz = samplingFreqHz;
        s.windowGroup = windowGroup;
        s.windowSeconds = windowSeconds;
        s.featureCount = featureCount;
        return s;
    }

    public Long getSensorId() {
        return sensorId;
    }

    public SensorType getSensorType() {
        return sensorType;
    }

    public String getChannelName() {
        return channelName;
    }

    public String getLocation() {
        return location;
    }

    public String getAxis() {
        return axis;
    }

    public double getSamplingFreqHz() {
        return samplingFreqHz;
    }

    public WindowGroup getWindowGroup() {
        return windowGroup;
    }

    public double getWindowSeconds() {
        return windowSeconds;
    }

    public int getFeatureCount() {
        return featureCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Sensor other)) return false;
        return sensorId != null && sensorId.equals(other.sensorId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(sensorId);
    }
}
