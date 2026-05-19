-- =============================================================================
-- V2  Indexes, stored procedures, log trigger, compression & retention policies.
--
-- Closes the gaps in V1:
--   * Adds FK from users.role_id -> roles.role_id (was missing).
--   * Adds indexes for the hot query paths (anomaly_logs by severity/time,
--     anomaly_results latest-N, feature_vectors by time + scenario).
--   * Defines sp_anomaly_summary / sp_model_stats — called by DashboardQueryAdapter
--     but not previously migrated.
--   * Adds a trigger that auto-creates an anomaly_log row whenever a row in
--     anomaly_results with is_anomaly=true is inserted (the application code
--     assumes this trigger exists).
--   * Adds TimescaleDB compression and 90-day retention on the hypertables.
-- =============================================================================

-- ---- 1. Missing FK on users.role_id -----------------------------------------
ALTER TABLE users
    ADD CONSTRAINT fk_users_role_id FOREIGN KEY (role_id)
        REFERENCES roles(role_id) ON DELETE RESTRICT;

-- ---- 2. Indexes for hot query paths -----------------------------------------
-- anomaly_logs.findByFilter(severity, start, end) ORDER BY time DESC
CREATE INDEX IF NOT EXISTS ix_anomaly_logs_time_desc       ON anomaly_logs ("time" DESC);
CREATE INDEX IF NOT EXISTS ix_anomaly_logs_severity_time   ON anomaly_logs (severity, "time" DESC);

-- anomaly_results: TimescaleDB already creates a time-DESC index on the
-- partitioning column. Add a partial index for the anomaly-only queries.
CREATE INDEX IF NOT EXISTS ix_anomaly_results_anomaly_time
    ON anomaly_results ("time" DESC) WHERE is_anomaly = TRUE;

-- feature_vectors: scenario filter + range scan
CREATE INDEX IF NOT EXISTS ix_feature_vectors_scenario_time
    ON feature_vectors (scenario_label, "time" DESC);

-- users: lookups by email / username are in the hot path of /auth/login + register
CREATE INDEX IF NOT EXISTS ix_users_email_lower    ON users (LOWER(email));
CREATE INDEX IF NOT EXISTS ix_users_username_lower ON users (LOWER(username));

-- ---- 3. Severity classification + auto-log trigger --------------------------
-- Matches AnomalyLog.classifySeverity in the application layer.
CREATE OR REPLACE FUNCTION fn_classify_severity(err DOUBLE PRECISION, thr DOUBLE PRECISION)
RETURNS VARCHAR LANGUAGE plpgsql IMMUTABLE AS $$
BEGIN
    IF thr <= 0 THEN
        RETURN 'LOW';
    END IF;
    IF err / thr >= 2.0 THEN RETURN 'HIGH';   END IF;
    IF err / thr >= 1.5 THEN RETURN 'MEDIUM'; END IF;
    RETURN 'LOW';
END;
$$;

CREATE OR REPLACE FUNCTION fn_log_anomaly()
RETURNS TRIGGER LANGUAGE plpgsql AS $$
BEGIN
    IF NEW.is_anomaly THEN
        INSERT INTO anomaly_logs ("time", reconstruction_error, threshold, severity, created_at)
        VALUES (NEW."time",
                NEW.reconstruction_error,
                NEW.threshold,
                fn_classify_severity(NEW.reconstruction_error, NEW.threshold),
                now());
    END IF;
    RETURN NEW;
END;
$$;

DROP TRIGGER IF EXISTS trg_log_anomaly ON anomaly_results;
CREATE TRIGGER trg_log_anomaly
    AFTER INSERT ON anomaly_results
    FOR EACH ROW
    EXECUTE FUNCTION fn_log_anomaly();

-- ---- 4. Stored procedures for the dashboard --------------------------------
-- sp_anomaly_summary(start, end) -> (severity, total_count, avg_score, max_score)
CREATE OR REPLACE FUNCTION sp_anomaly_summary(p_start TIMESTAMPTZ, p_end TIMESTAMPTZ)
RETURNS TABLE (
    severity     VARCHAR,
    total_count  BIGINT,
    avg_score    DOUBLE PRECISION,
    max_score    DOUBLE PRECISION
) LANGUAGE sql STABLE AS $$
    SELECT severity,
           COUNT(*)::BIGINT                    AS total_count,
           COALESCE(AVG(reconstruction_error), 0) AS avg_score,
           COALESCE(MAX(reconstruction_error), 0) AS max_score
    FROM anomaly_logs
    WHERE "time" >= p_start AND "time" < p_end
    GROUP BY severity
    ORDER BY severity;
$$;

-- sp_model_stats(start, end) -> single row
CREATE OR REPLACE FUNCTION sp_model_stats(p_start TIMESTAMPTZ, p_end TIMESTAMPTZ)
RETURNS TABLE (
    total_inferences BIGINT,
    anomaly_count    BIGINT,
    normal_count     BIGINT,
    avg_error        DOUBLE PRECISION,
    max_error        DOUBLE PRECISION,
    anomaly_rate     DOUBLE PRECISION
) LANGUAGE sql STABLE AS $$
    SELECT COUNT(*)::BIGINT                                AS total_inferences,
           COUNT(*) FILTER (WHERE is_anomaly)::BIGINT      AS anomaly_count,
           COUNT(*) FILTER (WHERE NOT is_anomaly)::BIGINT  AS normal_count,
           COALESCE(AVG(reconstruction_error), 0)          AS avg_error,
           COALESCE(MAX(reconstruction_error), 0)          AS max_error,
           CASE WHEN COUNT(*) = 0 THEN 0
                ELSE COUNT(*) FILTER (WHERE is_anomaly)::DOUBLE PRECISION
                     / COUNT(*)::DOUBLE PRECISION
           END AS anomaly_rate
    FROM anomaly_results
    WHERE "time" >= p_start AND "time" < p_end;
$$;

-- ---- 5. TimescaleDB compression + retention ---------------------------------
-- Compress chunks older than 7 days (good for 90+% size reduction on numeric data).
ALTER TABLE feature_vectors
    SET (timescaledb.compress, timescaledb.compress_segmentby = 'scenario_label');
ALTER TABLE anomaly_results
    SET (timescaledb.compress);

SELECT add_compression_policy('feature_vectors',  INTERVAL '7 days', if_not_exists => TRUE);
SELECT add_compression_policy('anomaly_results',  INTERVAL '7 days', if_not_exists => TRUE);

-- Drop chunks older than 90 days. Adjust for your retention policy.
SELECT add_retention_policy('feature_vectors',    INTERVAL '90 days', if_not_exists => TRUE);
SELECT add_retention_policy('anomaly_results',    INTERVAL '90 days', if_not_exists => TRUE);
