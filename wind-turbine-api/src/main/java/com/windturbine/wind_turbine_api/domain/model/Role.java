package com.windturbine.wind_turbine_api.domain.model;

import java.time.Instant;
import java.util.Objects;

/**
 * Authorization role.
 * Seed data: admin, operator, viewer.
 */
public class Role {

    private Long roleId;
    private String roleName;
    private String description;
    private Instant createdAt;

    private Role() {
        // For static rehydrate factory only.
    }

    public Role(String roleName, String description) {
        if (roleName == null || roleName.isBlank()) {
            throw new IllegalArgumentException("Role name cannot be empty.");
        }
        this.roleName = roleName;
        this.description = description;
        this.createdAt = Instant.now();
    }

    /**
     * Rebuilds a Role from persistence without re-running construction-time validation.
     * Used by mappers in the infrastructure layer.
     */
    public static Role rehydrate(Long roleId, String roleName, String description, Instant createdAt) {
        Role r = new Role();
        r.roleId = roleId;
        r.roleName = roleName;
        r.description = description;
        r.createdAt = createdAt;
        return r;
    }

    public Long getRoleId() {
        return roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public String getDescription() {
        return description;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Role other)) return false;
        return roleId != null && roleId.equals(other.roleId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(roleId);
    }
}
