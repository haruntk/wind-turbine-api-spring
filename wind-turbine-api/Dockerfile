# ============================================================================
# Wind Turbine API — production image.
#
# 1) builder  — full JDK + Maven; resolves deps in a cached layer, builds jar.
# 2) layers   — uses Spring Boot's layertools to crack the fat jar into
#               (dependencies / spring-boot-loader / snapshot-dependencies /
#                application) so unchanged layers stay cached across deploys.
# 3) runtime  — small JRE-only image, non-root user, healthcheck, JVM tuned
#               for containers.
# ============================================================================

# -----------------------------------------------------------------------------
# Stage 1 — Build
# -----------------------------------------------------------------------------
FROM eclipse-temurin:21-jdk-jammy AS builder
WORKDIR /workspace

# Cache Maven deps first. Copy only what affects dep resolution.
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
RUN chmod +x mvnw \
 && ./mvnw -B -q -DskipTests dependency:go-offline

# Now copy sources and build.
COPY src/ src/
RUN ./mvnw -B -q -DskipTests package \
 && cp target/wind-turbine-api.jar app.jar \
 && java -Djarmode=tools -jar app.jar extract --layers --launcher --destination extracted

# -----------------------------------------------------------------------------
# Stage 2 — Runtime
# -----------------------------------------------------------------------------
FROM eclipse-temurin:21-jre-jammy AS runtime

# wget is used by the HEALTHCHECK below (Debian slim ships without curl).
RUN apt-get update \
 && apt-get install -y --no-install-recommends wget tini \
 && rm -rf /var/lib/apt/lists/*

# Non-root runtime user.
RUN groupadd --system --gid 1001 spring \
 && useradd  --system --uid 1001 --gid spring --home-dir /app --shell /usr/sbin/nologin spring

WORKDIR /app

# Copy Spring Boot layered jar (dependencies first for max layer reuse).
COPY --from=builder --chown=spring:spring /workspace/extracted/dependencies/          ./
COPY --from=builder --chown=spring:spring /workspace/extracted/spring-boot-loader/    ./
COPY --from=builder --chown=spring:spring /workspace/extracted/snapshot-dependencies/ ./
COPY --from=builder --chown=spring:spring /workspace/extracted/application/           ./

USER spring:spring

# Containers: limit heap relative to cgroup memory and fail fast on OOM.
# Caller can append more flags via JAVA_OPTS.
ENV JAVA_TOOL_OPTIONS="\
    -XX:MaxRAMPercentage=75.0 \
    -XX:InitialRAMPercentage=50.0 \
    -XX:+ExitOnOutOfMemoryError \
    -XX:+HeapDumpOnOutOfMemoryError \
    -XX:HeapDumpPath=/tmp \
    -Djava.security.egd=file:/dev/./urandom \
    -Duser.timezone=UTC \
    -Dfile.encoding=UTF-8" \
    SPRING_PROFILES_ACTIVE=prod \
    SERVER_PORT=8080 \
    MANAGEMENT_PORT=8081

EXPOSE 8080 8081

# Liveness probe — uses the unauthenticated probe endpoint on the management port.
HEALTHCHECK --interval=20s --timeout=3s --start-period=40s --retries=3 \
    CMD wget -qO- "http://127.0.0.1:${MANAGEMENT_PORT:-8081}/actuator/health/liveness" \
        | grep -q '"status":"UP"' || exit 1

# tini = PID 1 reaping, clean SIGTERM forwarding for graceful shutdown.
ENTRYPOINT ["/usr/bin/tini", "--", "java", "org.springframework.boot.loader.launch.JarLauncher"]
