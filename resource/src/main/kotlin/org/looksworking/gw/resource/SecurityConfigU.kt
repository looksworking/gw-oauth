//package org.looksworking.gw.resource
//
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import org.springframework.security.config.web.server.ServerHttpSecurity
//import org.springframework.security.web.server.SecurityWebFilterChain
//
//@Configuration
//class SecurityConfigU {
//
//    @Bean
//    fun springSecurityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
//        http
//                .authorizeExchange()
//                .pathMatchers("/res")
//                .hasAuthority("SCOPE_resource.read")
//                .anyExchange()
//                .authenticated()
//                .and()
//                .oauth2ResourceServer()
//                .jwt()
//        return http.build()
//    }
//}