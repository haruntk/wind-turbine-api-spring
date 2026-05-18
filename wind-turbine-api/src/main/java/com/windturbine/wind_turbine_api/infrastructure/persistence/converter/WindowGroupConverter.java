package com.windturbine.wind_turbine_api.infrastructure.persistence.converter;

import com.windturbine.wind_turbine_api.domain.enums.WindowGroup;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Maps {@link WindowGroup} to the DB's lowercase string form
 * (e.g. {@code BEARING} &lt;-&gt; {@code "bearing"}).
 */
@Converter(autoApply = false)
public class WindowGroupConverter implements AttributeConverter<WindowGroup, String> {

    @Override
    public String convertToDatabaseColumn(WindowGroup attribute) {
        return attribute == null ? null : attribute.name().toLowerCase();
    }

    @Override
    public WindowGroup convertToEntityAttribute(String dbData) {
        return dbData == null ? null : WindowGroup.valueOf(dbData.toUpperCase());
    }
}
