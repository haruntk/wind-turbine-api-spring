package com.windturbine.wind_turbine_api.domain.model;

import com.windturbine.wind_turbine_api.domain.enums.Severity;

import java.time.Instant;
import java.util.Objects;

/**
 * Anomaly alert log entry — auto-created by a database trigger
 * when an anomaly is detected.
 */
public class AnomalyLog {

    public static final double HIGH_SEVERITY_RATIO = 2.0;

    public static final double MEDIUM_SEVERITY_RATIO = 1.5;

    private Long logId;
    private Instant time;
    private double reconstructionError;
    private double threshold;
    private Severity severity;
    private Instant createdAt;

    private AnomalyLog() {
        // For static rehydrate factory only.
    }

    public AnomalyLog(Instant time, double reconstructionError, double threshold) {
        if (threshold <= 0) {
            throw new IllegalArgumentException("Threshold must be positive.");
        }
        this.time = time;
        this.reconstructionError = reconstructionError;
        this.threshold = threshold;
        this.severity = classifySeverity(reconstructionError, threshold);
        this.createdAt = Instant.now();
    }

    // for reading the log in db
    public static AnomalyLog rehydrate(Long logId,
                                       Instant time,
                                       double reconstructionError,
                                       double threshold,
                                       Severity severity,
                                       Instant createdAt) {
        AnomalyLog log = new AnomalyLog();
        log.logId = logId;
        log.time = time;
        log.reconstructionError = reconstructionError;
        log.threshold = threshold;
        log.severity = severity;
        log.createdAt = createdAt;
        return log;
    }

    /**
     * Classifies severity from the ratio of reconstruction error to threshold.
     * HIGH:   ratio &gt;= 2.0
     * MEDIUM: ratio &gt;= 1.5
     * LOW:    otherwise
     */
    public static Severity classifySeverity(double reconstructionError, double threshold) {
        if (threshold == 0) {
            return Severity.LOW;
        }
        double ratio = reconstructionError / threshold;
        if (ratio >= HIGH_SEVERITY_RATIO) return Severity.HIGH;
        if (ratio >= MEDIUM_SEVERITY_RATIO) return Severity.MEDIUM;
        return Severity.LOW;
    }

    public Long getLogId() {
        return logId;
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

    public Severity getSeverity() {
        return severity;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    // equals logId'lere gore karsilastirma yapar
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AnomalyLog other)) return false;
        return logId != null && logId.equals(other.logId);
    }
    // eger equals metoduna göre iki obje birbirine eşitse, bu iki objenin hashCode() metodu kesinlikle aynı tam sayıyı (integer) döndürmelidir.
    // yukarida equals logId'ye gore hesaplandigi icin hashcode'da logId'ye gore hesaplanmalidir.
    @Override
    public int hashCode() {
        return Objects.hashCode(logId);
    }
}
