//package org.looksworking.gw.gateway.conf
//
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import org.springframework.security.config.web.server.ServerHttpSecurity
//import org.springframework.security.web.server.SecurityWebFilterChain
//
//@Configuration
//class SecurityConfig {
//
//    @Bean
//    fun configure(http: ServerHttpSecurity): SecurityWebFilterChain {
//        http
//                .authorizeExchange()
//                .pathMatchers("/resource").hasAuthority("read")
//                .anyExchange().authenticated()
//                .and()
//                .oauth2ResourceServer()
//                .jwt()
//        return http.build()
//    }
//}