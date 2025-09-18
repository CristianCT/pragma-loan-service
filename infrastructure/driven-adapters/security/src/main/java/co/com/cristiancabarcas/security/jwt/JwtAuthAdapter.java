package co.com.cristiancabarcas.security.jwt;

import co.com.cristiancabarcas.api.gateways.JwtAuthRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class JwtAuthAdapter implements JwtAuthRepository {

    private final JwtUtil jwtUtil;

    public JwtAuthAdapter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Mono<String> getUserName(String token) {
        return Mono.just(jwtUtil.extractUsername(token.replace("Bearer ", "")));
    }
}
