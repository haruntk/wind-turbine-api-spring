package com.windturbine.wind_turbine_api.domain.model;

import java.time.Instant;
import java.util.Objects;

/**
 * System user.
 * Mutation is allowed only through explicit business methods.
 */
public class User {

    private Long userId;
    private String username;
    private String email;
    private String passwordHash;
    private Long roleId;
    private boolean active;
    private Instant createdAt;
    private Instant lastLogin;

    private User() {
        // For static rehydrate factory only.
    }

    public User(String username, String email, String passwordHash, Long roleId) {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Username cannot be empty.");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email cannot be empty.");
        }
        if (passwordHash == null || passwordHash.isBlank()) {
            throw new IllegalArgumentException("Password hash cannot be empty.");
        }
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.roleId = roleId;
        this.active = true;
        this.createdAt = Instant.now();
    }

    /**
     * Rebuilds a User from persistence, bypassing construction-time validation.
     */
    public static User rehydrate(Long userId,
                                 String username,
                                 String email,
                                 String passwordHash,
                                 Long roleId,
                                 boolean active,
                                 Instant createdAt,
                                 Instant lastLogin) {
        User u = new User();
        u.userId = userId;
        u.username = username;
        u.email = email;
        u.passwordHash = passwordHash;
        u.roleId = roleId;
        u.active = active;
        u.createdAt = createdAt;
        u.lastLogin = lastLogin;
        return u;
    }

    public void updateProfile(String username, String email) {
        if (username != null) {
            if (username.isBlank()) {
                throw new IllegalArgumentException("Username cannot be empty.");
            }
            this.username = username;
        }
        if (email != null) {
            if (email.isBlank()) {
                throw new IllegalArgumentException("Email cannot be empty.");
            }
            this.email = email;
        }
    }

    public void assignRole(Long roleId) {
        this.roleId = roleId;
    }

    public void recordLogin() {
        this.lastLogin = Instant.now();
    }

    public void deactivate() {
        this.active = false;
    }

    public void activate() {
        this.active = true;
    }

    public void updatePasswordHash(String newPasswordHash) {
        if (newPasswordHash == null || newPasswordHash.isBlank()) {
            throw new IllegalArgumentException("Password hash cannot be empty.");
        }
        this.passwordHash = newPasswordHash;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public Long getRoleId() {
        return roleId;
    }

    public boolean isActive() {
        return active;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getLastLogin() {
        return lastLogin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User other)) return false;
        return userId != null && userId.equals(other.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(userId);
    }
}
