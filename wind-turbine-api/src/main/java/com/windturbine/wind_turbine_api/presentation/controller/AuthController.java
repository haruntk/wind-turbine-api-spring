package com.windturbine.wind_turbine_api.presentation.controller;

import com.windturbine.wind_turbine_api.application.dto.auth.AuthResponseDto;
import com.windturbine.wind_turbine_api.application.dto.auth.LoginRequestDto;
import com.windturbine.wind_turbine_api.application.dto.auth.RegisterRequestDto;
import com.windturbine.wind_turbine_api.application.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Public authentication endpoints.
 * Both routes are anonymous — wired as permit-all in SecurityFilterChain (Step 7).
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public AuthResponseDto login(@Valid @RequestBody LoginRequestDto request) {
        return authService.login(request);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthResponseDto register(@Valid @RequestBody RegisterRequestDto request) {
        return authService.register(request);
    }
}
