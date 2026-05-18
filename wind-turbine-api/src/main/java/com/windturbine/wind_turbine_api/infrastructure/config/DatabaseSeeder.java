package com.windturbine.wind_turbine_api.infrastructure.config;

import com.windturbine.wind_turbine_api.infrastructure.persistence.entity.RoleJpaEntity;
import com.windturbine.wind_turbine_api.infrastructure.persistence.repository.RoleJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

/**
 * Seeds initial necessary data into the database upon application startup.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class DatabaseSeeder implements CommandLineRunner {

    private final RoleJpaRepository roleJpaRepository;

    @Override
    @Transactional
    public void run(String... args) {
        log.info("Checking database seed...");

        // Sadece tabloda hiç veri yoksa (ilk kurulumsa) çalışır.
        if (roleJpaRepository.count() == 0) {
            log.info("No roles found in database. Seeding default roles...");

            RoleJpaEntity admin = RoleJpaEntity.builder()
                    .roleName("admin")
                    .description("Administrator with full access")
                    .createdAt(Instant.now())
                    .build();

            RoleJpaEntity operator = RoleJpaEntity.builder()
                    .roleName("operator")
                    .description("Operator who can manage sensors and view anomalies")
                    .createdAt(Instant.now())
                    .build();

            RoleJpaEntity viewer = RoleJpaEntity.builder()
                    .roleName("viewer")
                    .description("Viewer with read-only access")
                    .createdAt(Instant.now())
                    .build();

            roleJpaRepository.saveAll(List.of(admin, operator, viewer));
            log.info("Default roles (admin, operator, viewer) seeded successfully.");
        } else {
            log.info("Database is already seeded.");
        }
    }
}
