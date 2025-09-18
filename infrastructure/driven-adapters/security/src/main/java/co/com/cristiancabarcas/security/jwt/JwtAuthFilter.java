package co.com.cristiancabarcas.security.jwt;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

@Component
public class JwtAuthFilter implements WebFilter {

    private final JwtUtil jwtUtil;
    private final Map<String, List<String>> rolePermissions = Map.of(
            "CUSTOMER", List.of("/api/v1/loan"),
            "ADVISOR", List.of("/api/v1/loans")
    );
    private static final Logger log = Logger.getLogger(JwtAuthFilter.class.getName());

    public JwtAuthFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String path = exchange.getRequest().getPath().value();

        String token = getTokenFromRequest(exchange);
        if (Objects.isNull(token) || token.isEmpty()) {
            log.warning("Token is empty or null");
            return unauthorizedResponse(exchange, "Unauthorized: Token is missing");
        }

        if (jwtUtil.isValidToken(token)) {
            String username = jwtUtil.extractUsername(token);
            String role = jwtUtil.extractRoles(token);

            List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + role));

            if (!hasRolePermissions(role, path)) {
                log.warning("Forbidden: Insufficient permissions");
                return unauthorizedResponse(exchange, "Forbidden: Insufficient permissions");
            }

            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(username, null, authorities);

            return chain.filter(exchange)
                    .contextWrite(ReactiveSecurityContextHolder.withAuthentication(auth));
        }

        log.warning("Invalid token");
        return unauthorizedResponse(exchange, "Unauthorized: Invalid token");
    }

    private boolean hasRolePermissions(String role, String path) {
        return rolePermissions.containsKey(role)
                && rolePermissions.get(role).contains(path);
    }

    private String getTokenFromRequest(ServerWebExchange exchange) {
        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }

    private Mono<Void> unauthorizedResponse(ServerWebExchange exchange, String message) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        exchange.getResponse().getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        exchange.getResponse().getHeaders().add("Access-Control-Allow-Origin", "*");

        String jsonResponse = "{\"success\":false,\"message\":\"" + message + "\",\"data\":null}";
        DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(jsonResponse.getBytes());

        return exchange.getResponse().writeWith(Mono.just(buffer));
    }

}