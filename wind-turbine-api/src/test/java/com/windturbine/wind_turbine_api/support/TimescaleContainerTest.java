package com.windturbine.wind_turbine_api.support;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

/**
 * Base for any integration test that needs a real database.
 * Uses a TimescaleDB image and runs the real Flyway migrations.
 * <p>
 * Tip: keep the container static so it is reused across all subclasses
 * (Testcontainers will start it once per JVM and tear it down via the
 * Ryuk reaper).
 */
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class TimescaleContainerTest {

    @SuppressWarnings("resource")
    static final PostgreSQLContainer<?> TIMESCALE =
            new PostgreSQLContainer<>(DockerImageName
                    .parse("timescale/timescaledb:latest-pg16")
                    .asCompatibleSubstituteFor("postgres"))
                    .withDatabaseName("windguard")
                    .withUsername("test")
                    .withPassword("test")
                    .withReuse(true);

    static {
        TIMESCALE.start();
    }

    @DynamicPropertySource
    static void registerProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url",      TIMESCALE::getJdbcUrl);
        registry.add("spring.datasource.username", TIMESCALE::getUsername);
        registry.add("spring.datasource.password", TIMESCALE::getPassword);

        // Provide non-secret JWT config so JwtProperties passes validation.
        registry.add("jwt.secret-key",          () -> "integration-test-secret-key-please-ignore-me-32");
        registry.add("jwt.issuer",              () -> "test-issuer");
        registry.add("jwt.audience",            () -> "test-audience");
        registry.add("jwt.expiration-minutes",  () -> 10);
        registry.add("cors.allowed-origins",    () -> "http://localhost:3000");
    }
}
