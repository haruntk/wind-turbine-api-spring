package com.windturbine.wind_turbine_api.infrastructure.persistence.converter;

import com.windturbine.wind_turbine_api.domain.enums.SensorType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Maps {@link SensorType} to the DB's snake_case string form
 * (e.g. {@code WIND_VANE} &lt;-&gt; {@code "wind_vane"}).
 */
@Converter(autoApply = false)
public class SensorTypeConverter implements AttributeConverter<SensorType, String> {

    @Override
    public String convertToDatabaseColumn(SensorType attribute) {
        return attribute == null ? null : attribute.name().toLowerCase();
    }

    @Override
    public SensorType convertToEntityAttribute(String dbData) {
        return dbData == null ? null : SensorType.valueOf(dbData.toUpperCase());
    }
}
