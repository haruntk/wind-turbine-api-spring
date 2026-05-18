package com.windturbine.wind_turbine_api.domain.model;

import java.time.Instant;

/**
 * LSTM Autoencoder inference result.
 * Stored in a TimescaleDB hypertable; the time column is the partitioning key.
 */
public class AnomalyResult {

    public static final String DEFAULT_MODEL_VERSION = "v1";

    private Instant time;
    private double reconstructionError;
    private double threshold;
    private boolean anomaly;
    private String modelVersion;

    private AnomalyResult() {
        // For static rehydrate factory only.
    }

    public AnomalyResult(Instant time,
                         double reconstructionError,
                         double threshold,
                         String modelVersion) {
        if (reconstructionError < 0) {
            throw new IllegalArgumentException("Reconstruction error cannot be negative.");
        }
        if (threshold <= 0) {
            throw new IllegalArgumentException("Threshold must be positive.");
        }
        this.time = time;
        this.reconstructionError = reconstructionError;
        this.threshold = threshold;
        this.anomaly = reconstructionError >= threshold;
        this.modelVersion = modelVersion != null ? modelVersion : DEFAULT_MODEL_VERSION;
    }

    public static AnomalyResult rehydrate(Instant time,
                                          double reconstructionError,
                                          double threshold,
                                          boolean anomaly,
                                          String modelVersion) {
        AnomalyResult r = new AnomalyResult();
        r.time = time;
        r.reconstructionError = reconstructionError;
        r.threshold = threshold;
        r.anomaly = anomaly;
        r.modelVersion = modelVersion;
        return r;
    }

    public Instant getTime() {
        return time;
    }

    public double getReconstructionError() {
        return reconstructionError;
    }

    public double getThreshold() {
        return threshold;
    }

    public boolean isAnomaly() {
        return anomaly;
    }

    public String getModelVersion() {
        return modelVersion;
    }
}
