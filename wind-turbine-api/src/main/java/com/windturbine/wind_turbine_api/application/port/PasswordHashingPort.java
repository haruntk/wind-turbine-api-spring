package com.windturbine.wind_turbine_api.application.port;

public interface PasswordHashingPort {
    String hash(String rawPassword);

    boolean matches(String rawPassword, String hashedPassword);
}
