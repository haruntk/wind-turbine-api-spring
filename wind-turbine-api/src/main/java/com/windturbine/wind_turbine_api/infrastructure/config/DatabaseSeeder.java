package com.windturbine.wind_turbine_api.infrastructure.config;

import com.windturbine.wind_turbine_api.infrastructure.persistence.entity.RoleJpaEntity;
import com.windturbine.wind_turbine_api.infrastructure.persistence.repository.RoleJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Seeds the canonical role rows. Idempotent: skips any role already present,
 * so it is safe to run on every startup, including rolling deploys.
 * <p>
 * Disable when a separate migration tool owns seed data:
 * {@code app.seeder.enabled=false}.
 */
@Component
@RequiredArgsConstructor
@Slf4j
@ConditionalOnProperty(name = "app.seeder.enabled", havingValue = "true", matchIfMissing = true)
public class DatabaseSeeder implements ApplicationRunner {

    private static final List<RoleSeed> ROLE_SEEDS = List.of(
            new RoleSeed("admin",    "Administrator with full access"),
            new RoleSeed("operator", "Operator who can manage sensors and view anomalies"),
            new RoleSeed("viewer",   "Viewer with read-only access")
    );

    private final RoleJpaRepository roleJpaRepository;

    @Override
    @Transactional
    public void run(org.springframework.boot.ApplicationArguments args) {
        Set<String> existing = roleJpaRepository.findAll().stream()
                .map(RoleJpaEntity::getRoleName)
                .collect(Collectors.toSet());

        List<RoleJpaEntity> toInsert = ROLE_SEEDS.stream()
                .filter(s -> !existing.contains(s.name()))
                .map(s -> RoleJpaEntity.builder()
                        .roleName(s.name())
                        .description(s.description())
                        .createdAt(Instant.now())
                        .build())
                .toList();

        if (toInsert.isEmpty()) {
            log.info("Role seed: all canonical roles already present.");
            return;
        }
        roleJpaRepository.saveAll(toInsert);
        log.info("Role seed: inserted {} new roles ({}).",
                toInsert.size(),
                toInsert.stream().map(RoleJpaEntity::getRoleName).toList());
    }

    private record RoleSeed(String name, String description) {}
}
