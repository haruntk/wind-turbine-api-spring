package com.windturbine.wind_turbine_api.application.mapper;

import com.windturbine.wind_turbine_api.application.dto.user.UserDto;
import com.windturbine.wind_turbine_api.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Domain User -> UserDto (response payload).
 */
@Mapper(componentModel = "spring")
public interface UserDtoMapper {

    @Mapping(source = "user.userId", target = "userId")
    @Mapping(source = "user.username", target = "username")
    @Mapping(source = "user.email", target = "email")
    @Mapping(source = "roleName", target = "roleName")
    @Mapping(source = "user.active", target = "active")
    @Mapping(source = "user.createdAt", target = "createdAt")
    @Mapping(source = "user.lastLogin", target = "lastLogin")
    UserDto toDto(User user, String roleName);
}
