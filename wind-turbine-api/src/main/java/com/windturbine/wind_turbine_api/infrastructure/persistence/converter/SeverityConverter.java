package com.windturbine.wind_turbine_api.infrastructure.persistence.converter;

import com.windturbine.wind_turbine_api.domain.enums.Severity;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Maps {@link Severity} to the DB's UPPERCASE string form.
 * Strictly speaking {@code @Enumerated(EnumType.STRING)} would work here,
 */
@Converter(autoApply = false)
public class SeverityConverter implements AttributeConverter<Severity, String> {

    @Override
    public String convertToDatabaseColumn(Severity attribute) {
        return attribute == null ? null : attribute.name();
    }

    @Override
    public Severity convertToEntityAttribute(String dbData) {
        return dbData == null ? null : Severity.valueOf(dbData.toUpperCase());
    }
}
