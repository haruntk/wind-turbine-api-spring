package com.windturbine.wind_turbine_api.application.service;

import com.windturbine.wind_turbine_api.application.dto.user.UpdateUserRequestDto;
import com.windturbine.wind_turbine_api.application.dto.user.UserDto;
import com.windturbine.wind_turbine_api.application.mapper.UserDtoMapper;
import com.windturbine.wind_turbine_api.domain.exception.NotFoundException;
import com.windturbine.wind_turbine_api.domain.model.Role;
import com.windturbine.wind_turbine_api.domain.model.User;
import com.windturbine.wind_turbine_api.domain.port.out.RoleRepositoryPort;
import com.windturbine.wind_turbine_api.domain.port.out.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Admin user-management use cases.
 * Pulls the .NET UsersController's business logic out of the HTTP layer.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserManagementService {

    private final UserRepositoryPort userRepositoryPort;
    private final RoleRepositoryPort roleRepositoryPort;
    private final UserDtoMapper userDtoMapper;

    public List<UserDto> getAll() {
        // Single roles query, then a map lookup per user — avoids N+1.
        Map<Long, String> roleNames = roleRepositoryPort.findAll().stream()
                .collect(Collectors.toMap(Role::getRoleId, Role::getRoleName));

        return userRepositoryPort.findAll().stream()
                .map(u -> userDtoMapper.toDto(u, roleNames.getOrDefault(u.getRoleId(), "")))
                .toList();
    }

    public UserDto getById(Long userId) {
        User user = userRepositoryPort.findById(userId)
                .orElseThrow(() -> new NotFoundException("User", userId));
        return userDtoMapper.toDto(user, resolveRoleName(user));
    }

    @Transactional
    public UserDto update(Long userId, UpdateUserRequestDto request) {
        User user = userRepositoryPort.findById(userId)
                .orElseThrow(() -> new NotFoundException("User", userId));

        // updateProfile guards against blank but allows nulls (partial update).
        user.updateProfile(request.username(), request.email());

        if (request.active() != null) {
            if (request.active()) {
                user.activate();
            } else {
                user.deactivate();
            }
        }

        if (request.roleId() != null) {
            Role role = roleRepositoryPort.findById(request.roleId())
                    .orElseThrow(() -> new NotFoundException("Role", request.roleId()));
            user.assignRole(role.getRoleId());
        }

        User saved = userRepositoryPort.save(user);
        return userDtoMapper.toDto(saved, resolveRoleName(saved));
    }

    @Transactional
    public void delete(Long userId) {
        User user = userRepositoryPort.findById(userId)
                .orElseThrow(() -> new NotFoundException("User", userId));
        userRepositoryPort.delete(user);
    }

    private String resolveRoleName(User user) {
        return roleRepositoryPort.findById(user.getRoleId())
                .map(Role::getRoleName)
                .orElse("");
    }
}
