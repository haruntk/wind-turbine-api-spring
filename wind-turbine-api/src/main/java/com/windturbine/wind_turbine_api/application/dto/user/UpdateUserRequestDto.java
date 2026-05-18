package com.windturbine.wind_turbine_api.application.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

/**
 * Partial update payload — all fields nullable.
 * <p>
 * Jakarta validation annotations skip null values by default, so omitting a
 * field is always valid; only present fields are checked.
 */
public record UpdateUserRequestDto(

        @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters.")
        String username,

        @Email(message = "A valid email address is required.")
        String email,

        Boolean active,

        @Positive(message = "Role ID must be a positive integer.")
        Long roleId
) {
}
