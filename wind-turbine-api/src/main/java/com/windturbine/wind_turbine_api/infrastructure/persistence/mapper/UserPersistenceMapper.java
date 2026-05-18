package com.windturbine.wind_turbine_api.infrastructure.persistence.mapper;

import com.windturbine.wind_turbine_api.domain.model.User;
import com.windturbine.wind_turbine_api.infrastructure.persistence.entity.UserJpaEntity;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * MapStruct çevirici: User to UserJpaEntity.
 */
@Mapper(componentModel = "spring")
public interface UserPersistenceMapper {

    UserJpaEntity toJpa(User user);

    default User toDomain(UserJpaEntity jpa) {
        if (jpa == null) {
            return null;
        }
        return User.rehydrate(
                jpa.getUserId(),
                jpa.getUsername(),
                jpa.getEmail(),
                jpa.getPasswordHash(),
                jpa.getRoleId(),
                jpa.isActive(),
                jpa.getCreatedAt(),
                jpa.getLastLogin()
        );
    }

    default List<User> toDomainList(List<UserJpaEntity> entities) {
        return entities.stream().map(this::toDomain).toList();
    }
}
