package com.windturbine.wind_turbine_api.infrastructure.persistence.adapter;

import com.windturbine.wind_turbine_api.domain.model.Role;
import com.windturbine.wind_turbine_api.domain.port.out.RoleRepositoryPort;
import com.windturbine.wind_turbine_api.infrastructure.persistence.mapper.RolePersistenceMapper;
import com.windturbine.wind_turbine_api.infrastructure.persistence.repository.RoleJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * RoleRepositoryPort'un infrastructure tarafındaki uygulaması.
 */
@Component
@RequiredArgsConstructor
public class RolePersistenceAdapter implements RoleRepositoryPort {

    private final RoleJpaRepository roleJpaRepository;
    private final RolePersistenceMapper mapper;

    @Override
    public Optional<Role> findById(Long roleId) {
        return roleJpaRepository.findById(roleId).map(mapper::toDomain);
    }

    @Override
    public Optional<Role> findByName(String roleName) {
        return roleJpaRepository.findByRoleName(roleName).map(mapper::toDomain);
    }

    @Override
    public List<Role> findAll() {
        return mapper.toDomainList(roleJpaRepository.findAll());
    }
}
