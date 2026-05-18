package com.windturbine.wind_turbine_api.domain.model;

import java.time.Instant;

/**
 * 426-dimensional feature vector extracted from sensor data via FFT.
 * Stored in a TimescaleDB hypertable; the time column is the partitioning key.
 */
public class FeatureVector {

    public static final int FEATURE_DIMENSION = 426;

    private Instant time;
    private String scenarioLabel;
    private double[] features;

    private FeatureVector() {
        // For static rehydrate factory only.
    }

    public FeatureVector(Instant time, double[] features, String scenarioLabel) {
        if (features == null || features.length == 0) {
            throw new IllegalArgumentException("Features array cannot be null or empty.");
        }
        this.time = time;
        this.features = features;
        this.scenarioLabel = scenarioLabel != null ? scenarioLabel : ScenarioLabels.UNKNOWN;
    }

    public static FeatureVector rehydrate(Instant time, String scenarioLabel, double[] features) {
        FeatureVector fv = new FeatureVector();
        fv.time = time;
        fv.scenarioLabel = scenarioLabel;
        fv.features = features;
        return fv;
    }

    public Instant getTime() {
        return time;
    }

    public String getScenarioLabel() {
        return scenarioLabel;
    }

    public double[] getFeatures() {
        return features;
    }

    /**
     * Scenario label constants.
     */
    public static final class ScenarioLabels {
        public static final String UNKNOWN = "unknown";
        public static final String HEALTHY = "healthy";
        public static final String MASS_IMBALANCE = "mass_imbalance";
        public static final String AERODYNAMIC_IMBALANCE = "aerodynamic_imbalance";
        public static final String ICING_BLADE = "icing_blade";

        private ScenarioLabels() {
        }
    }
}
