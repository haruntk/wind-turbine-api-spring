package com.windturbine.wind_turbine_api.application.service;

import com.windturbine.wind_turbine_api.application.dto.anomaly.AnomalyLogDto;
import com.windturbine.wind_turbine_api.application.dto.anomaly.AnomalyResultDto;
import com.windturbine.wind_turbine_api.application.mapper.AnomalyLogDtoMapper;
import com.windturbine.wind_turbine_api.application.mapper.AnomalyResultDtoMapper;
import com.windturbine.wind_turbine_api.application.util.QueryArgs;
import com.windturbine.wind_turbine_api.domain.enums.Severity;
import com.windturbine.wind_turbine_api.domain.exception.NotFoundException;
import com.windturbine.wind_turbine_api.domain.model.AnomalyLog;
import com.windturbine.wind_turbine_api.domain.port.out.AnomalyLogRepositoryPort;
import com.windturbine.wind_turbine_api.domain.port.out.AnomalyResultRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AnomalyService {

    private final AnomalyResultRepositoryPort anomalyResultRepositoryPort;
    private final AnomalyLogRepositoryPort anomalyLogRepositoryPort;
    private final AnomalyResultDtoMapper anomalyResultDtoMapper;
    private final AnomalyLogDtoMapper anomalyLogDtoMapper;

    public List<AnomalyResultDto> getResultsByRange(Instant start, Instant end) {
        QueryArgs.requireValidRange(start, end);
        return anomalyResultDtoMapper.toDtoList(
                anomalyResultRepositoryPort.findByRange(start, end)
        );
    }

    public List<AnomalyResultDto> getLatestResults(int limit) {
        QueryArgs.requireValidLimit(limit);
        return anomalyResultDtoMapper.toDtoList(
                anomalyResultRepositoryPort.findLatest(limit)
        );
    }

    public List<AnomalyLogDto> getLogs(String severityFilter, Instant start, Instant end) {
        Severity severity = parseSeverity(severityFilter);
        return anomalyLogDtoMapper.toDtoList(
                anomalyLogRepositoryPort.findByFilter(severity, start, end)
        );
    }

    public AnomalyLogDto getLogById(Long logId) {
        AnomalyLog log = anomalyLogRepositoryPort.findById(logId)
                .orElseThrow(() -> new NotFoundException("AnomalyLog", logId));
        return anomalyLogDtoMapper.toDto(log);
    }

    private static Severity parseSeverity(String severity) {
        if (severity == null || severity.isBlank()) {
            return null;
        }
        try {
            return Severity.valueOf(severity.toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Invalid severity. Use: LOW, MEDIUM, or HIGH.");
        }
    }
}
