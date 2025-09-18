package co.com.cristiancabarcas.api.gateways;

import reactor.core.publisher.Mono;

public interface JwtAuthRepository {
    Mono<String> getUserName(String token);
}
