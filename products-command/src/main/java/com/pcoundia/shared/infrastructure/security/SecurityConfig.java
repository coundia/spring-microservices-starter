package com.pcoundia.shared.infrastructure.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    private static final String GATEWAY_HEADER = "X-Gateway-Auth";

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        ReactiveAuthorizationManager<AuthorizationContext> gatewayAuthManager = (authentication, context) -> {
            boolean authorized = context.getExchange().getRequest().getHeaders().containsKey(GATEWAY_HEADER);
            return Mono.just(new AuthorizationDecision(authorized));
        };

        http.authorizeExchange(exchanges -> exchanges
                       // .pathMatchers("/**").access(gatewayAuthManager)
                        .anyExchange().permitAll()
                )
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint((exchange, ex) -> Mono.fromRunnable(() ->
                                exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN)
                        ))
                )
                .csrf(csrf -> csrf.disable());

        return http.build();
    }
}
