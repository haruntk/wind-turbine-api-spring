package com.windturbine.wind_turbine_api.infrastructure.persistence.adapter;

import com.windturbine.wind_turbine_api.application.dto.anomaly.AnomalySummaryDto;
import com.windturbine.wind_turbine_api.application.dto.dashboard.ModelStatsDto;
import com.windturbine.wind_turbine_api.application.port.DashboardQueryPort;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

/**
 * Adapter implementing {@link DashboardQueryPort} via native SQL.
 * <p>
 * Calls the database-side stored procedures {@code sp_anomaly_summary}
 * and {@code sp_model_stats} and maps the result rows directly to DTOs.
 * <p>
 * Number values arrive as {@code BigDecimal} or {@code Long} depending on
 * the column type; we coerce them through {@link Number} for safety.
 */
@Component
@RequiredArgsConstructor
public class DashboardQueryAdapter implements DashboardQueryPort {

    private static final String ANOMALY_SUMMARY_SQL = """
            SELECT severity, total_count, avg_score, max_score
            FROM sp_anomaly_summary(:p_start, :p_end)
            """;

    private static final String MODEL_STATS_SQL = """
            SELECT total_inferences, anomaly_count, normal_count,
                   avg_error, max_error, anomaly_rate
            FROM sp_model_stats(:p_start, :p_end)
            """;

    private final EntityManager entityManager;

    @Override
    @SuppressWarnings("unchecked")
    public List<AnomalySummaryDto> getAnomalySummary(Instant start, Instant end) {
        List<Tuple> rows = entityManager.createNativeQuery(ANOMALY_SUMMARY_SQL, Tuple.class)
                .setParameter("p_start", start)
                .setParameter("p_end", end)
                .getResultList();

        return rows.stream()
                .map(r -> new AnomalySummaryDto(
                        r.get("severity", String.class),
                        r.get("total_count", Number.class).longValue(),
                        r.get("avg_score", Number.class).doubleValue(),
                        r.get("max_score", Number.class).doubleValue()
                ))
                .toList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public ModelStatsDto getModelStats(Instant start, Instant end) {
        List<Tuple> rows = entityManager.createNativeQuery(MODEL_STATS_SQL, Tuple.class)
                .setParameter("p_start", start)
                .setParameter("p_end", end)
                .getResultList();

        if (rows.isEmpty()) {
            return new ModelStatsDto(0L, 0L, 0L, 0.0, 0.0, 0.0);
        }
        Tuple r = rows.get(0);
        return new ModelStatsDto(
                r.get("total_inferences", Number.class).longValue(),
                r.get("anomaly_count", Number.class).longValue(),
                r.get("normal_count", Number.class).longValue(),
                r.get("avg_error", Number.class).doubleValue(),
                r.get("max_error", Number.class).doubleValue(),
                r.get("anomaly_rate", Number.class).doubleValue()
        );
    }
}
