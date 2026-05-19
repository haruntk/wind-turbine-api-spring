package com.windturbine.wind_turbine_api.infrastructure.security.config;

/**
 * Spring Security adds a sensible default set of response headers
 * (X-Content-Type-Options, X-Frame-Options=DENY, Cache-Control). HSTS is only
 * applied over HTTPS, which is correct.
 * <p>
 * Headers are wired in {@link SecurityConfig#securityFilterChain} via
 * {@code http.headers(...)}; this file documents the policy in one place so
 * reviewers do not have to grep the chain.
 *
 * Applied headers:
 *   - Strict-Transport-Security: max-age=31536000; includeSubDomains  (HTTPS only)
 *   - X-Content-Type-Options:    nosniff
 *   - X-Frame-Options:           DENY
 *   - Referrer-Policy:           strict-origin-when-cross-origin
 *   - Cross-Origin-Opener-Policy: same-origin
 *
 * A Content-Security-Policy is intentionally NOT set for an API — CSP is a
 * browser-document concern and would be noise on JSON responses.
 */
final class SecureHeadersConfig {
    private SecureHeadersConfig() {}
}
