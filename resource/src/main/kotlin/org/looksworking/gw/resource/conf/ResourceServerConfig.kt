//package org.looksworking.gw.resource.conf
//
//import org.springframework.context.annotation.Configuration
//import org.springframework.security.config.annotation.web.builders.HttpSecurity
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
//
//@EnableResourceServer
//@Configuration
//class ResourceServerConfig: WebSecurityConfigurerAdapter() {
//
//    override fun configure(http: HttpSecurity) {
//        http.cors()
//                .and()
//                .authorizeRequests()
//                .antMatchers("/res/**")
//                .hasAuthority("read")
//                .anyRequest()
//                .authenticated()
//                .and().oauth2ResourceServer().opaqueToken()
//    }
//}