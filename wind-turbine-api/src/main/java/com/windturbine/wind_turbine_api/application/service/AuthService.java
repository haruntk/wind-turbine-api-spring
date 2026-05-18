package com.windturbine.wind_turbine_api.application.service;

import com.windturbine.wind_turbine_api.application.dto.auth.AuthResponseDto;
import com.windturbine.wind_turbine_api.application.dto.auth.LoginRequestDto;
import com.windturbine.wind_turbine_api.application.dto.auth.RegisterRequestDto;
import com.windturbine.wind_turbine_api.application.port.TokenServicePort;
import com.windturbine.wind_turbine_api.domain.exception.ConflictException;
import com.windturbine.wind_turbine_api.domain.exception.InvalidCredentialsException;
import com.windturbine.wind_turbine_api.domain.exception.NotFoundException;
import com.windturbine.wind_turbine_api.domain.model.Role;
import com.windturbine.wind_turbine_api.domain.model.User;
import com.windturbine.wind_turbine_api.domain.port.out.RoleRepositoryPort;
import com.windturbine.wind_turbine_api.domain.port.out.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Authentication use cases: login and registration.
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private static final String DEFAULT_ROLE_NAME = "viewer";

    private final UserRepositoryPort userRepositoryPort;
    private final RoleRepositoryPort roleRepositoryPort;
    private final PasswordEncoder passwordEncoder;
    private final TokenServicePort tokenServicePort;

    @Transactional
    public AuthResponseDto login(LoginRequestDto request) {
        User user = userRepositoryPort.findByEmail(request.email())
                .filter(User::isActive)
                .orElseThrow(InvalidCredentialsException::new);

        if (!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            throw new InvalidCredentialsException();
        }

        user.recordLogin();
        User saved = userRepositoryPort.save(user);

        Role role = roleRepositoryPort.findById(saved.getRoleId())
                .orElseThrow(() -> new NotFoundException("Role", saved.getRoleId()));

        return buildAuthResponse(saved, role.getRoleName());
    }

    @Transactional
    public AuthResponseDto register(RegisterRequestDto request) {
        if (userRepositoryPort.existsByEmail(request.email())) {
            throw new ConflictException("Email is already registered.");
        }
        if (userRepositoryPort.existsByUsername(request.username())) {
            throw new ConflictException("Username is already taken.");
        }

        Role defaultRole = roleRepositoryPort.findByName(DEFAULT_ROLE_NAME)
                .orElseThrow(() -> new NotFoundException("Role", DEFAULT_ROLE_NAME));

        User newUser = new User(
                request.username(),
                request.email(),
                passwordEncoder.encode(request.password()),
                defaultRole.getRoleId()
        );

        User saved = userRepositoryPort.save(newUser);
        return buildAuthResponse(saved, defaultRole.getRoleName());
    }

    private AuthResponseDto buildAuthResponse(User user, String roleName) {
        TokenServicePort.GeneratedToken token = tokenServicePort.generateToken(user, roleName);
        return new AuthResponseDto(
                token.token(),
                user.getUsername(),
                user.getEmail(),
                roleName,
                token.expiresAt()
        );
    }
}
