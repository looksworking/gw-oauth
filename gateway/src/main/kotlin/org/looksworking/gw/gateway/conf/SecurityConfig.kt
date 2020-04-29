package org.looksworking.gw.gateway.conf

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain

@Configuration
@EnableWebFluxSecurity
class SecurityConfig {

    @Bean
    fun configure(http: ServerHttpSecurity): SecurityWebFilterChain {
        http
                .authorizeExchange()
                .anyExchange().authenticated()
                .and()
                .oauth2Login()
                .and().logout().logoutUrl("/exitGateway")
                .and().csrf().disable()

        return http.build()
    }
}