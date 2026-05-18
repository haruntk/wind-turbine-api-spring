package com.windturbine.wind_turbine_api.domain.port.out;

import com.windturbine.wind_turbine_api.domain.model.Role;

import java.util.List;
import java.util.Optional;

/**
 * Outbound port for Role persistence operations.
 */
public interface RoleRepositoryPort {

    Optional<Role> findById(Long roleId);

    Optional<Role> findByName(String roleName);

    List<Role> findAll();
}
