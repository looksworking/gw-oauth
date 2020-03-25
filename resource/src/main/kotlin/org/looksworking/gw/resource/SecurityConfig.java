package org.looksworking.gw.resource;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

public class SecurityConfig {

  @Bean
  SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) throws Exception {
    http
        .authorizeExchange()
          .pathMatchers("/res")
            .hasAuthority("read")
          .anyExchange()
            .authenticated()
          .and()
        .oauth2ResourceServer()
          .jwt();
    return http.build();
  }
}