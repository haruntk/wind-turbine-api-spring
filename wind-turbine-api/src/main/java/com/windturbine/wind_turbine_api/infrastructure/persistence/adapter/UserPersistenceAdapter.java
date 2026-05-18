package com.windturbine.wind_turbine_api.infrastructure.persistence.adapter;

import com.windturbine.wind_turbine_api.domain.model.User;
import com.windturbine.wind_turbine_api.domain.port.out.UserRepositoryPort;
import com.windturbine.wind_turbine_api.infrastructure.persistence.entity.UserJpaEntity;
import com.windturbine.wind_turbine_api.infrastructure.persistence.mapper.UserPersistenceMapper;
import com.windturbine.wind_turbine_api.infrastructure.persistence.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * UserRepositoryPort'un infrastructure tarafındaki uygulaması.
 */
@Component
@RequiredArgsConstructor
public class UserPersistenceAdapter implements UserRepositoryPort {

    private final UserJpaRepository userJpaRepository;
    private final UserPersistenceMapper mapper;

    @Override
    public Optional<User> findById(Long userId) {
        return userJpaRepository.findById(userId).map(mapper::toDomain);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userJpaRepository.findByEmail(email).map(mapper::toDomain);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userJpaRepository.findByUsername(username).map(mapper::toDomain);
    }

    @Override
    public List<User> findAll() {
        return mapper.toDomainList(userJpaRepository.findAll());
    }

    @Override
    public User save(User user) {
        UserJpaEntity toSave = mapper.toJpa(user);
        UserJpaEntity saved = userJpaRepository.save(toSave);
        return mapper.toDomain(saved);
    }

    @Override
    public void delete(User user) {
        if (user.getUserId() == null) {
            return;
        }
        userJpaRepository.deleteById(user.getUserId());
    }

    @Override
    public boolean existsByEmail(String email) {
        return userJpaRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userJpaRepository.existsByUsername(username);
    }
}
