-- 1. TimescaleDB Eklentisini aktif et
CREATE EXTENSION IF NOT EXISTS timescaledb;

-- 2. Temel Tablolar (Standart PostgreSQL)
CREATE TABLE roles (
    role_id BIGSERIAL PRIMARY KEY,
    role_name VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    created_at TIMESTAMP(6) WITH TIME ZONE NOT NULL,
    CONSTRAINT uq_roles_role_name UNIQUE (role_name)
);

CREATE TABLE users (
    user_id BIGSERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    role_id BIGINT NOT NULL,
    is_active BOOLEAN NOT NULL,
    created_at TIMESTAMP(6) WITH TIME ZONE NOT NULL,
    last_login TIMESTAMP(6) WITH TIME ZONE,
    CONSTRAINT uq_users_username UNIQUE (username),
    CONSTRAINT uq_users_email UNIQUE (email)
);

CREATE TABLE sensors (
    sensor_id BIGSERIAL PRIMARY KEY,
    sensor_type VARCHAR(255) NOT NULL,
    channel_name VARCHAR(255) NOT NULL,
    location VARCHAR(255),
    axis VARCHAR(255),
    sampling_freq_hz DOUBLE PRECISION NOT NULL,
    window_group VARCHAR(255) NOT NULL,
    window_seconds DOUBLE PRECISION NOT NULL,
    feature_count INT NOT NULL,
    CONSTRAINT uq_sensors_channel_name UNIQUE (channel_name)
);

CREATE TABLE anomaly_logs (
    log_id BIGSERIAL PRIMARY KEY,
    "time" TIMESTAMP(6) WITH TIME ZONE NOT NULL,
    reconstruction_error DOUBLE PRECISION NOT NULL,
    threshold DOUBLE PRECISION NOT NULL,
    severity VARCHAR(255) NOT NULL,
    created_at TIMESTAMP(6) WITH TIME ZONE NOT NULL
);

-- 3. TimescaleDB Hypertable'lar (Zaman Serisi Tabloları)

CREATE TABLE feature_vectors (
    "time" TIMESTAMP(6) WITH TIME ZONE NOT NULL,
    scenario_label VARCHAR(255),
    features DOUBLE PRECISION[],
    PRIMARY KEY ("time")
);
SELECT create_hypertable('feature_vectors', 'time', if_not_exists => TRUE);

CREATE TABLE anomaly_results (
    "time" TIMESTAMP(6) WITH TIME ZONE NOT NULL,
    reconstruction_error DOUBLE PRECISION NOT NULL,
    threshold DOUBLE PRECISION NOT NULL,
    is_anomaly BOOLEAN NOT NULL,
    model_version VARCHAR(255) NOT NULL,
    PRIMARY KEY ("time")
);
SELECT create_hypertable('anomaly_results', 'time', if_not_exists => TRUE);
