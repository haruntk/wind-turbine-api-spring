package com.windturbine.wind_turbine_api.presentation.controller;

import com.windturbine.wind_turbine_api.application.dto.user.UpdateUserRequestDto;
import com.windturbine.wind_turbine_api.application.dto.user.UserDto;
import com.windturbine.wind_turbine_api.application.service.UserManagementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Admin-only user management endpoints.
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class UsersController {

    private final UserManagementService userManagementService;

    @GetMapping
    public List<UserDto> getAll() {
        return userManagementService.getAll();
    }

    @GetMapping("/{id}")
    public UserDto getById(@PathVariable Long id) {
        return userManagementService.getById(id);
    }

    @PutMapping("/{id}")
    public UserDto update(@PathVariable Long id,
                          @Valid @RequestBody UpdateUserRequestDto request) {
        return userManagementService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        userManagementService.delete(id);
    }
}
