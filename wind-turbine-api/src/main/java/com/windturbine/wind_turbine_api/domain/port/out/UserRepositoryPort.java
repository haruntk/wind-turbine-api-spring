package com.windturbine.wind_turbine_api.domain.port.out;

import com.windturbine.wind_turbine_api.domain.model.User;

import java.util.List;
import java.util.Optional;

/**
 * Outbound port for User persistence operations.
 * Implemented by an adapter in the infrastructure layer.
 */
public interface UserRepositoryPort {

    Optional<User> findById(Long userId);

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    List<User> findAll();

    User save(User user);

    void delete(User user);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);
}
