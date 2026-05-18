package com.windturbine.wind_turbine_api.infrastructure.persistence.mapper;

import com.windturbine.wind_turbine_api.domain.model.Role;
import com.windturbine.wind_turbine_api.infrastructure.persistence.entity.RoleJpaEntity;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * MapStruct: Role to RoleJpaEntity.
 */
@Mapper(componentModel = "spring")
public interface RolePersistenceMapper {

    /**
     * Domain → JPA.
     */
    RoleJpaEntity toJpa(Role role);

    /**
     * JPA → Domain. Domain Role'de public setter yok, bu yüzden
     * MapStruct otomatik üretemez — manuel olarak rehydrate factory'yi çağırıyoruz.
     */
    default Role toDomain(RoleJpaEntity jpa) {
        if (jpa == null) {
            return null;
        }
        return Role.rehydrate(
                jpa.getRoleId(),
                jpa.getRoleName(),
                jpa.getDescription(),
                jpa.getCreatedAt()
        );
    }

    default List<Role> toDomainList(List<RoleJpaEntity> entities) {
        return entities.stream().map(this::toDomain).toList();
    }
}
