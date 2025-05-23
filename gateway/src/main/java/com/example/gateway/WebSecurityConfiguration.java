package com.example.gateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@EnableWebFluxSecurity
@Configuration
class WebSecurityConfiguration {

	@Bean
	public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
		return http
				.authorizeExchange(authorize -> authorize
						.pathMatchers("/services/**").authenticated()
						.pathMatchers("/**").permitAll()
				).csrf(ServerHttpSecurity.CsrfSpec::disable)
				.oauth2ResourceServer((resourceServer) -> resourceServer
						.jwt(withDefaults())
				)
				.build();
	}
}